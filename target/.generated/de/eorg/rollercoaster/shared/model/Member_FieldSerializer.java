package de.eorg.rollercoaster.shared.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Member_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAWSAccessKey(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::AWSAccessKey;
  }-*/;
  
  private static native void setAWSAccessKey(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::AWSAccessKey = value;
  }-*/;
  
  private static native java.lang.String getAWSSecretKey(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::AWSSecretKey;
  }-*/;
  
  private static native void setAWSSecretKey(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::AWSSecretKey = value;
  }-*/;
  
  private static native java.lang.String getEmail(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::email;
  }-*/;
  
  private static native void setEmail(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::email = value;
  }-*/;
  
  private static native java.lang.String getFirstname(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::firstname;
  }-*/;
  
  private static native void setFirstname(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::firstname = value;
  }-*/;
  
  private static native java.lang.String getLastname(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::lastname;
  }-*/;
  
  private static native void setLastname(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::lastname = value;
  }-*/;
  
  private static native java.lang.String getNickname(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::nickname;
  }-*/;
  
  private static native void setNickname(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::nickname = value;
  }-*/;
  
  private static native java.lang.String getProfilePic(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::profilePic;
  }-*/;
  
  private static native void setProfilePic(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::profilePic = value;
  }-*/;
  
  private static native de.eorg.rollercoaster.shared.model.UserRole getRole(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::role;
  }-*/;
  
  private static native void setRole(de.eorg.rollercoaster.shared.model.Member instance, de.eorg.rollercoaster.shared.model.UserRole value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::role = value;
  }-*/;
  
  private static native boolean getShowWelcomeInfo(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::showWelcomeInfo;
  }-*/;
  
  private static native void setShowWelcomeInfo(de.eorg.rollercoaster.shared.model.Member instance, boolean value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::showWelcomeInfo = value;
  }-*/;
  
  private static native java.lang.String getSocialId(de.eorg.rollercoaster.shared.model.Member instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.Member::socialId;
  }-*/;
  
  private static native void setSocialId(de.eorg.rollercoaster.shared.model.Member instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.Member::socialId = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.eorg.rollercoaster.shared.model.Member instance) throws SerializationException {
    setAWSAccessKey(instance, streamReader.readString());
    setAWSSecretKey(instance, streamReader.readString());
    setEmail(instance, streamReader.readString());
    setFirstname(instance, streamReader.readString());
    setLastname(instance, streamReader.readString());
    setNickname(instance, streamReader.readString());
    setProfilePic(instance, streamReader.readString());
    setRole(instance, (de.eorg.rollercoaster.shared.model.UserRole) streamReader.readObject());
    setShowWelcomeInfo(instance, streamReader.readBoolean());
    setSocialId(instance, streamReader.readString());
    
  }
  
  public static de.eorg.rollercoaster.shared.model.Member instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.eorg.rollercoaster.shared.model.Member();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.eorg.rollercoaster.shared.model.Member instance) throws SerializationException {
    streamWriter.writeString(getAWSAccessKey(instance));
    streamWriter.writeString(getAWSSecretKey(instance));
    streamWriter.writeString(getEmail(instance));
    streamWriter.writeString(getFirstname(instance));
    streamWriter.writeString(getLastname(instance));
    streamWriter.writeString(getNickname(instance));
    streamWriter.writeString(getProfilePic(instance));
    streamWriter.writeObject(getRole(instance));
    streamWriter.writeBoolean(getShowWelcomeInfo(instance));
    streamWriter.writeString(getSocialId(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.eorg.rollercoaster.shared.model.Member_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.Member_FieldSerializer.deserialize(reader, (de.eorg.rollercoaster.shared.model.Member)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.Member_FieldSerializer.serialize(writer, (de.eorg.rollercoaster.shared.model.Member)object);
  }
  
}
