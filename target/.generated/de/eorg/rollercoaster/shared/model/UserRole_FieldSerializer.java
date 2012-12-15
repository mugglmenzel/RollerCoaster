package de.eorg.rollercoaster.shared.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class UserRole_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getDefaultMemberId(de.eorg.rollercoaster.shared.model.UserRole instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.UserRole::defaultMemberId;
  }-*/;
  
  private static native void setDefaultMemberId(de.eorg.rollercoaster.shared.model.UserRole instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.UserRole::defaultMemberId = value;
  }-*/;
  
  private static native java.lang.String getRoleName(de.eorg.rollercoaster.shared.model.UserRole instance) /*-{
    return instance.@de.eorg.rollercoaster.shared.model.UserRole::roleName;
  }-*/;
  
  private static native void setRoleName(de.eorg.rollercoaster.shared.model.UserRole instance, java.lang.String value) 
  /*-{
    instance.@de.eorg.rollercoaster.shared.model.UserRole::roleName = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.eorg.rollercoaster.shared.model.UserRole instance) throws SerializationException {
    // Enum deserialization is handled via the instantiate method
  }
  
  public static de.eorg.rollercoaster.shared.model.UserRole instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int ordinal = streamReader.readInt();
    de.eorg.rollercoaster.shared.model.UserRole[] values = de.eorg.rollercoaster.shared.model.UserRole.values();
    assert (ordinal >= 0 && ordinal < values.length);
    return values[ordinal];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.eorg.rollercoaster.shared.model.UserRole instance) throws SerializationException {
    assert (instance != null);
    streamWriter.writeInt(instance.ordinal());
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.eorg.rollercoaster.shared.model.UserRole_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.UserRole_FieldSerializer.deserialize(reader, (de.eorg.rollercoaster.shared.model.UserRole)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.eorg.rollercoaster.shared.model.UserRole_FieldSerializer.serialize(writer, (de.eorg.rollercoaster.shared.model.UserRole)object);
  }
  
}
