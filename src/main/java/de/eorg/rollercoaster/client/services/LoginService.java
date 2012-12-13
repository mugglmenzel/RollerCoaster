package de.eorg.rollercoaster.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.eorg.rollercoaster.shared.model.LoginInfo;
import de.eorg.rollercoaster.shared.model.Member;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	public LoginInfo login(String requestUri) throws Exception;

	public Member registerMember(Member member);

	public Member updateMember(Member member);
}
