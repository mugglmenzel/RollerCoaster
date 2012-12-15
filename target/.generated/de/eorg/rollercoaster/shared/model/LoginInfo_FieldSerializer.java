package de.eorg.rollercoaster.shared.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class LoginInfo_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native boolean getLoggedIn(de.eorg.rollercoaster.shared.model.LoginInfo instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.LoginInfo::loggedIn;
  }-*/;
  
  private static native void setLoggedIn(de.eorg.rollercoaster.shared.model.LoginInfo instance, boolean value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.LoginInfo::loggedIn = value;
  }-*/;
  
  private static native java.lang.String getLoginUrl(de.eorg.rollercoaster.shared.model.LoginInfo instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.LoginInfo::loginUrl;
  }-*/;
  
  private static native void setLoginUrl(de.eorg.rollercoaster.shared.model.LoginInfo instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.LoginInfo::loginUrl = value;
  }-*/;
  
  private static native java.lang.String getLogoutUrl(de.eorg.rollercoaster.shared.model.LoginInfo instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.LoginInfo::logoutUrl;
  }-*/;
  
  private static native void setLogoutUrl(de.eorg.rollercoaster.shared.model.LoginInfo instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.LoginInfo::logoutUrl = value;
  }-*/;
  
  private static native de.eorg.rollercoaster.shared.model.Member getMember(de.eorg.rollercoaster.shared.model.LoginInfo instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.LoginInfo::member;
  }-*/;
  
  private static native void setMember(de.eorg.rollercoaster.shared.model.LoginInfo instance, de.eorg.rollercoaster.shared.model.Member value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.LoginInfo::member = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.eorg.rollercoaster.shared.model.LoginInfo instance) throws SerializationException {
    setLoggedIn(instance, streamReader.readBoolean());
    setLoginUrl(instance, streamReader.readString());
    setLogoutUrl(instance, streamReader.readString());
    setMember(instance, (de.eorg.rollercoaster.shared.model.Member) streamReader.readObject());
    
  }
  
  public static de.eorg.rollercoaster.shared.model.LoginInfo instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.eorg.rollercoaster.shared.model.LoginInfo();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.eorg.rollercoaster.shared.model.LoginInfo instance) throws SerializationException {
    streamWriter.writeBoolean(getLoggedIn(instance));
    streamWriter.writeString(getLoginUrl(instance));
    streamWriter.writeString(getLogoutUrl(instance));
    streamWriter.writeObject(getMember(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.eorg.rollercoaster.shared.model.LoginInfo_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.LoginInfo_FieldSerializer.deserialize(reader, (de.eorg.rollercoaster.shared.model.LoginInfo)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.LoginInfo_FieldSerializer.serialize(writer, (de.eorg.rollercoaster.shared.model.LoginInfo)object);
  }
  
}
