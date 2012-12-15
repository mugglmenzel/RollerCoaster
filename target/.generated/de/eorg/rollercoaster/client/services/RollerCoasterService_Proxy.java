package de.eorg.rollercoaster.client.services;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;

public class RollerCoasterService_Proxy extends RemoteServiceProxy implements de.eorg.rollercoaster.client.services.RollerCoasterServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "de.eorg.rollercoaster.client.services.RollerCoasterService";
  private static final String SERIALIZATION_POLICY ="F18E06A1424361C6526CBFCA6002AC85";
  private static final de.eorg.rollercoaster.client.services.RollerCoasterService_TypeSerializer SERIALIZER = new de.eorg.rollercoaster.client.services.RollerCoasterService_TypeSerializer();
  
  public RollerCoasterService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "rollercoaster", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  @Override
  public SerializationStreamWriter createStreamWriter() {
    ClientSerializationStreamWriter toReturn =
      (ClientSerializationStreamWriter) super.createStreamWriter();
    if (getRpcToken() != null) {
      toReturn.addFlags(ClientSerializationStreamWriter.FLAG_RPC_TOKEN_INCLUDED);
    }
    return toReturn;
  }
  @Override
  protected void checkRpcTokenType(RpcToken token) {
    if (!(token instanceof com.google.gwt.user.client.rpc.XsrfToken)) {
      throw new RpcTokenException("Invalid RpcToken type: expected 'com.google.gwt.user.client.rpc.XsrfToken' but got '" + token.getClass() + "'");
    }
  }
}
