package de.eorg.rollercoaster.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.amazonaws.services.simpledb.model.UpdateCondition;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.eorg.rollercoaster.client.OutOfQuotaException;
import de.eorg.rollercoaster.client.services.LoginService;
import de.eorg.rollercoaster.server.OAuth2Provider;
import de.eorg.rollercoaster.server.servlets.util.CookiesUtil;
import de.eorg.rollercoaster.shared.model.LoginInfo;
import de.eorg.rollercoaster.shared.model.Member;
import de.eorg.rollercoaster.shared.model.UserRole;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(LoginServiceImpl.class.getName());

	private UserService userService = UserServiceFactory.getUserService();

	private final String SDB_DOMAIN = "myownthemepark-members";

	public LoginInfo login(String requestUri) throws Exception {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoggedIn(false);
		loginInfo.setLoginUrl(userService.createLoginURL(requestUri));

		Map<String, String> cookies = CookiesUtil
				.getCookiesStringMap(getThreadLocalRequest().getCookies());
		log.info("Got cookies " + cookies);
		String oauthService = cookies.get("oauth.service");

		log.info("Logging in with OAuth service " + oauthService);

		if (oauthService != null) {
			try {
				String accessTokenString = cookies.get("oauth.accessToken");
				String accessSecret = cookies.get("oauth.secret");
				if (accessTokenString == null)
					return loginInfo;

				log.info("Retrieved access token " + accessTokenString);
				Token accessToken = new Token(accessTokenString, accessSecret);
				log.info("Token object " + accessToken.getToken() + ", "
						+ accessToken.getSecret());

				OAuth2Provider provider = OAuth2Provider.valueOf(oauthService);
				OAuthService service = provider.getOAuthService();

				Cookie serviceTokenCookie = new Cookie("oauth.service",
						provider.toString());
				serviceTokenCookie.setMaxAge(14 * 24 * 60 * 60);
				serviceTokenCookie.setPath("/");
				getThreadLocalResponse().addCookie(serviceTokenCookie);
				Cookie accessTokenCookie = new Cookie("oauth.accessToken",
						accessTokenString);
				accessTokenCookie.setMaxAge(14 * 24 * 60 * 60);
				accessTokenCookie.setPath("/");
				getThreadLocalResponse().addCookie(accessTokenCookie);
				Cookie accessSecretCookie = new Cookie("oauth.secret",
						accessSecret);
				accessSecretCookie.setMaxAge(14 * 24 * 60 * 60);
				accessSecretCookie.setPath("/");
				getThreadLocalResponse().addCookie(accessSecretCookie);

				if (OAuth2Provider.GOOGLE.equals(provider)) {
					OAuthRequest req = new OAuthRequest(Verb.GET,
							"https://www.googleapis.com/oauth2/v1/userinfo");
					service.signRequest(accessToken, req);
					Response response = req.send();
					log.info("Requested user info from google: "
							+ response.getBody());

					JSONObject googleUserInfo = new JSONObject(
							response.getBody());
					log.info("got user info: "
							+ googleUserInfo.getString("given_name") + ", "
							+ googleUserInfo.getString("family_name"));

					Member tempMember = findMemberBySocialId(googleUserInfo
							.getString("id"));

					if (tempMember == null) {
						tempMember = new Member();

						tempMember.setSocialId(googleUserInfo.getString("id"));
						tempMember.setFirstname(googleUserInfo
								.getString("given_name"));
						tempMember.setLastname(googleUserInfo
								.getString("family_name"));
						tempMember
								.setNickname(googleUserInfo.getString("name"));
						tempMember.setProfilePic(googleUserInfo
								.getString("picture"));

						req = new OAuthRequest(Verb.GET,
								"https://www.googleapis.com/plus/v1/people/me");
						service.signRequest(accessToken, req);
						response = req.send();
						log.info("Requested more user info from google: "
								+ response.getBody());

						JSONObject googleUserInfo2 = new JSONObject(
								response.getBody());
						log.info("got user info: "
								+ googleUserInfo2.getString("nickname") + ", "
								+ googleUserInfo2.getString("displayName"));
						if (googleUserInfo2 != null
								&& googleUserInfo2.getJSONArray("emails") != null)
							for (int i = 0; i < googleUserInfo2.getJSONArray(
									"emails").length(); i++) {
								JSONObject emailInfo = googleUserInfo2
										.getJSONArray("emails")
										.optJSONObject(i);
								if (emailInfo != null
										&& emailInfo.getBoolean("primary")) {
									tempMember.setEmail(emailInfo
											.getString("value"));
									tempMember = registerMember(tempMember);
									loginInfo.setLoggedIn(true);
									break;
								}
							}
					} else
						loginInfo.setLoggedIn(true);

					loginInfo.setMember(tempMember);

				} else if (OAuth2Provider.TWITTER.equals(provider)) {
					OAuthRequest req = new OAuthRequest(Verb.GET,
							"https://api.twitter.com/1/account/verify_credentials.json");
					service.signRequest(accessToken, req);
					log.info("Requesting from twitter " + req.getCompleteUrl());
					Response response = req.send();
					log.info("Requested user info from twitter: "
							+ response.getBody());
					JSONObject twitterUserInfo = new JSONObject(
							response.getBody());
					log.info("got user info: "
							+ twitterUserInfo.getString("name") + ", "
							+ twitterUserInfo.getString("screen_name"));

					Member tempMember = findMemberBySocialId(new Integer(
							twitterUserInfo.getInt("id")).toString());
					if (tempMember == null) {
						tempMember = new Member();
						tempMember.setSocialId(new Integer(twitterUserInfo
								.getInt("id")).toString());
						tempMember.setFirstname(twitterUserInfo.getString(
								"name").split(" ")[0]);
						tempMember.setLastname(twitterUserInfo
								.getString("name").split(" ", 2)[1]);
						tempMember.setNickname(twitterUserInfo
								.getString("screen_name"));
						tempMember.setProfilePic(twitterUserInfo
								.getString("profile_image_url"));
					} else
						loginInfo.setLoggedIn(true);
					loginInfo.setMember(tempMember);

				} else if (OAuth2Provider.FACEBOOK.equals(provider)) {
					OAuthRequest req = new OAuthRequest(Verb.GET,
							"https://graph.facebook.com/me");
					service.signRequest(accessToken, req);
					log.info("Requesting from facebook " + req.getCompleteUrl());
					Response response = req.send();
					log.info("Requested user info from facebook: "
							+ response.getBody());
					JSONObject facebookUserInfo = new JSONObject(
							response.getBody());
					log.info("got user info: "
							+ facebookUserInfo.getString("name") + ", "
							+ facebookUserInfo.getString("username"));

					Member tempMember = findMemberBySocialId(facebookUserInfo
							.getString("id"));
					if (tempMember == null) {
						tempMember = new Member();
						tempMember.setSocialId(new Integer(facebookUserInfo
								.getString("id")).toString());
						tempMember.setFirstname(facebookUserInfo
								.getString("first_name"));
						tempMember.setLastname(facebookUserInfo
								.getString("last_name"));
						tempMember.setNickname(facebookUserInfo
								.getString("username"));
						tempMember.setProfilePic("https://graph.facebook.com/"
								+ facebookUserInfo.getString("id")
								+ "/picture?type=large");
						tempMember
								.setEmail(facebookUserInfo.getString("email"));
						tempMember = registerMember(tempMember);
					}

					loginInfo.setLoggedIn(true);
					loginInfo.setMember(tempMember);
				}
				loginInfo.setLogoutUrl("/logout/oauth");
				log.info("Set loginInfo to " + loginInfo);
				return loginInfo;
			} catch (OverQuotaException oqe) {
				log.log(Level.WARNING, oqe.getLocalizedMessage(), oqe);
				throw new OutOfQuotaException("Out of Quota!", oqe);
			} catch (Exception e) {
				log.log(Level.WARNING, e.getLocalizedMessage(), e);
			}
		} else {

			User user = userService.getCurrentUser();

			if (userService.isUserLoggedIn() && user != null) {
				loginInfo.setLoggedIn(true);
				loginInfo.setMember(registerMember(user));
				loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
			}
			log.info("Logged in with google services " + loginInfo);
		}

		return loginInfo;
	}

	public Member registerMember(User user) {
		return null;
	}

	public Member registerMember(Member member) {
		AmazonSimpleDB asdb = getSimpleDB();

		asdb.putAttributes(new PutAttributesRequest(SDB_DOMAIN, member
				.getEmail(), memberToAttributeList(member),
				new UpdateCondition("email", member.getEmail(), false)));

		return member;
	}

	public Member updateMember(Member member) {
		AmazonSimpleDB asdb = getSimpleDB();

		asdb.putAttributes(new PutAttributesRequest(SDB_DOMAIN, member
				.getEmail(), memberToAttributeList(member),
				new UpdateCondition("email", member.getEmail(), true)));

		return member;
	}

	public Member findMemberBySocialId(String socialId) {
		AmazonSimpleDB asdb = getSimpleDB();

		SelectResult result = asdb.select(new SelectRequest("SELECT * FROM "
				+ SDB_DOMAIN + " WHERE socialId = \"" + socialId + "\"", true));

		Item resultItem = result.getItems().iterator().next();
		return attributeListToMember(resultItem.getAttributes());
	}

	private Member attributeListToMember(List<Attribute> attributes) {
		Member member = new Member();
		Iterator<Attribute> ita = attributes.iterator();
		while (ita.hasNext()) {
			Attribute a = ita.next();
			if ("email".equals(a.getName()))
				member.setEmail(a.getValue());
			else if ("socialId".equals(a.getName()))
				member.setSocialId(a.getValue());
			else if ("nickname".equals(a.getName()))
				member.setNickname(a.getValue());
			else if ("firstname".equals(a.getName()))
				member.setFirstname(a.getValue());
			else if ("lastname".equals(a.getName()))
				member.setLastname(a.getValue());
			else if ("profilePic".equals(a.getName()))
				member.setProfilePic(a.getValue());
			else if ("AWSAccessKey".equals(a.getName()))
				member.setAWSAccessKey(a.getValue());
			else if ("AWSSecretKey".equals(a.getName()))
				member.setAWSSecretKey(a.getValue());
			else if ("role".equals(a.getName()))
				member.setRole(UserRole.valueOf(a.getValue()));
			else if ("showWelcome".equals(a.getName()))
				member.setShowWelcomeInfo(new Boolean(a.getValue())
						.booleanValue());
		}

		return member;
	}

	private List<ReplaceableAttribute> memberToAttributeList(Member member) {
		List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
		attributes.add(new ReplaceableAttribute("email", member.getEmail(),
				false));
		attributes.add(new ReplaceableAttribute("socialId", member
				.getSocialId(), true));
		attributes.add(new ReplaceableAttribute("nickname", member
				.getNickname(), true));
		attributes.add(new ReplaceableAttribute("firstname", member
				.getFirstname(), true));
		attributes.add(new ReplaceableAttribute("lastname", member
				.getLastname(), true));
		attributes.add(new ReplaceableAttribute("profilePic", member
				.getProfilePic(), true));
		attributes.add(new ReplaceableAttribute("AWSAccessKey", member
				.getAWSAccessKey(), true));
		attributes.add(new ReplaceableAttribute("AWSSecretKey", member
				.getAWSSecretKey(), true));
		attributes.add(new ReplaceableAttribute("role",
				member.getRole().name(), true));
		attributes.add(new ReplaceableAttribute("showWelcome", new Boolean(
				member.isShowWelcomeInfo()).toString(), true));

		return attributes;
	}

	private AmazonSimpleDB getSimpleDB() {
		try {
			AWSCredentials credentials = new PropertiesCredentials(this
					.getClass().getResourceAsStream(
							"../AwsCredentials.properties"));
			AmazonSimpleDB asdb = new AmazonSimpleDBClient(credentials);
			asdb.createDomain(new CreateDomainRequest(SDB_DOMAIN));
			return asdb;
		} catch (IOException e) {
			log.throwing(this.getClass().getName(), "getSimpleDB", e);
		}
		return null;
	}

}
