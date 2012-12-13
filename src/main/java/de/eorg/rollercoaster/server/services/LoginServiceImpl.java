package de.eorg.rollercoaster.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.UpdateCondition;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.eorg.rollercoaster.client.services.LoginService;
import de.eorg.rollercoaster.shared.model.LoginInfo;
import de.eorg.rollercoaster.shared.model.Member;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(LoginServiceImpl.class.getName());

	private final String SDB_DOMAIN = "myownthemepark-members";

	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {

			loginInfo.setLoggedIn(true);
			loginInfo.getMember().setEmail(user.getEmail());
			loginInfo.getMember().setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
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
