package de.eorg.rollercoaster.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface RollerCoasterServiceAsync
{

    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static RollerCoasterServiceAsync instance;

        public static final RollerCoasterServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (RollerCoasterServiceAsync) GWT.create( RollerCoasterService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "rollercoaster" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
