package de.eorg.rollercoaster.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LoginServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see de.eorg.rollercoaster.client.services.LoginService
     */
    void login( java.lang.String requestUri, AsyncCallback<de.eorg.rollercoaster.shared.model.LoginInfo> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see de.eorg.rollercoaster.client.services.LoginService
     */
    void registerMember( de.eorg.rollercoaster.shared.model.Member member, AsyncCallback<de.eorg.rollercoaster.shared.model.Member> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see de.eorg.rollercoaster.client.services.LoginService
     */
    void updateMember( de.eorg.rollercoaster.shared.model.Member member, AsyncCallback<de.eorg.rollercoaster.shared.model.Member> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see de.eorg.rollercoaster.client.services.LoginService
     */
    void memberExists( java.lang.String email, AsyncCallback<java.lang.Boolean> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static LoginServiceAsync instance;

        public static final LoginServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (LoginServiceAsync) GWT.create( LoginService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "login" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
