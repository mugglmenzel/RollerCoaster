package de.eorg.rollercoaster.server.services;

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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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

import de.eorg.rollercoaster.client.exceptions.MemberExistsException;
import de.eorg.rollercoaster.client.exceptions.OutOfQuotaException;
import de.eorg.rollercoaster.client.services.LoginService;
import de.eorg.rollercoaster.server.AmazonCredentials;
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
		log.info("starting login procedure...");
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoggedIn(false);
		loginInfo.setLoginUrl(userService.createLoginURL(requestUri));

		log.info("reading cookies...");
		String oauthService = null;
		Map<String, String> cookies = null;
		try {
			cookies = CookiesUtil.getCookiesStringMap(getThreadLocalRequest()
					.getCookies());
			log.info("Got cookies " + cookies);
			oauthService = cookies.get("oauth.service");
		} catch (Exception e) {
			log.log(Level.WARNING, "Cannot read cookies!", e);
		}

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

				Member tempMember = null;

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

					tempMember = findMemberBySocialId(googleUserInfo
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
								+ googleUserInfo2.optString("displayName"));
						if (googleUserInfo2 != null
								&& googleUserInfo2.optJSONArray("emails") != null)
							for (int i = 0; i < googleUserInfo2.getJSONArray(
									"emails").length(); i++) {
								JSONObject emailInfo = googleUserInfo2
										.getJSONArray("emails")
										.optJSONObject(i);
								if (emailInfo != null
										&& emailInfo.optBoolean("primary")) {
									tempMember.setEmail(emailInfo
											.getString("value"));
									loginInfo.setLoggedIn(true);
									break;
								}
							}
						log.info("created temp member " + tempMember);
					} else
						loginInfo.setLoggedIn(true);

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

					tempMember = findMemberBySocialId(new Integer(
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
						log.info("created temp member " + tempMember);
					} else
						loginInfo.setLoggedIn(true);

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

					tempMember = findMemberBySocialId(facebookUserInfo
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
						loginInfo.setLoggedIn(true);
						log.info("created temp member " + tempMember);
					} else
						loginInfo.setLoggedIn(true);
				}
				loginInfo.setMember(loginInfo.isLoggedIn()
						|| tempMember.getEmail() == null ? tempMember
						: registerOrUpdateMember(tempMember));
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

	public Member registerMember(User user) throws Exception {
		Member member = new Member();
		member.setEmail(user.getEmail());
		member.setNickname(user.getNickname());
		member.setSocialId(user.getUserId());

		return registerMember(member);
	}

	private Member registerOrUpdateMember(Member member) {
		try {
			return registerMember(member);
		} catch (MemberExistsException e) {
			return updateMember(member);
		}
	}

	public boolean memberExists(String email) {
		AmazonSimpleDB asdb = getSimpleDB();

		log.info("Checking member " + email + " exists in SDB...");

		SelectResult exists = asdb.select(new SelectRequest("select * from `"
				+ SDB_DOMAIN + "` where itemName() = '" + email + "'", true));

		return exists.getItems().size() > 0;
	}

	public Member registerMember(Member member) throws MemberExistsException {
		AmazonSimpleDB asdb = getSimpleDB();

		if (!memberExists(member.getEmail())) {

			log.info("Storing member " + member.getSocialId() + " to SDB...");
			asdb.putAttributes(new PutAttributesRequest(SDB_DOMAIN, member
					.getEmail(), memberToAttributeList(member)));

			return member;
		} else
			throw new MemberExistsException(
					"Member cannot be registered. A member with same email address already exists.");
	}

	public Member updateMember(Member member) {
		AmazonSimpleDB asdb = getSimpleDB();

		asdb.putAttributes(new PutAttributesRequest(SDB_DOMAIN, member
				.getEmail(), memberToAttributeList(member),
				new UpdateCondition("email", member.getEmail(), true)));

		return member;
	}

	public Member findMemberBySocialId(String socialId) {
		if (socialId != null && !"".equals(socialId)) {
			AmazonSimpleDB asdb = getSimpleDB();

			log.info("Finding socialId " + socialId + " from SDB...");
			log.info("query: " + "select * from " + SDB_DOMAIN
					+ " where socialId = '" + socialId + "'");
			SelectResult result = asdb.select(new SelectRequest(
					"select * from `" + SDB_DOMAIN + "` where socialId = '"
							+ socialId + "'", true));

			if (result.getItems().size() > 0) {
				Item resultItem = result.getItems().iterator().next();
				log.info("Found member " + resultItem.getName());
				return attributeListToMember(resultItem.getAttributes());
			}
		}
		return null;
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
		attributes.add(new ReplaceableAttribute("email",
				member.getEmail() == null ? "" : member.getEmail(), false));
		attributes.add(new ReplaceableAttribute("socialId", member
				.getSocialId() == null ? "" : member.getSocialId(), true));
		attributes.add(new ReplaceableAttribute("nickname", member
				.getNickname() == null ? "" : member.getNickname(), true));
		attributes.add(new ReplaceableAttribute("firstname", member
				.getFirstname() == null ? "" : member.getFirstname(), true));
		attributes.add(new ReplaceableAttribute("lastname", member
				.getLastname() == null ? "" : member.getLastname(), true));
		attributes.add(new ReplaceableAttribute("profilePic", member
				.getProfilePic() == null ? "" : member.getProfilePic(), true));
		attributes.add(new ReplaceableAttribute("AWSAccessKey", member
				.getAWSAccessKey() == null ? "" : member.getAWSAccessKey(),
				true));
		attributes.add(new ReplaceableAttribute("AWSSecretKey", member
				.getAWSSecretKey() == null ? "" : member.getAWSSecretKey(),
				true));
		attributes.add(new ReplaceableAttribute("role",
				member.getRole().name(), true));
		attributes.add(new ReplaceableAttribute("showWelcome", new Boolean(
				member.isShowWelcomeInfo()).toString(), true));

		return attributes;
	}

	private AmazonSimpleDB getSimpleDB() {
		log.info("Connecting to SDB...");

		AWSCredentials credentials = new BasicAWSCredentials(
				AmazonCredentials.AWS_ACCESS_KEY,
				AmazonCredentials.AWS_SECRET_KEY);
		AmazonSimpleDB asdb = new AmazonSimpleDBClient(credentials,
				new ClientConfiguration());
		asdb.createDomain(new CreateDomainRequest(SDB_DOMAIN));
		log.info("Created domain, now returning SDB client...");
		return asdb;
	}

}
