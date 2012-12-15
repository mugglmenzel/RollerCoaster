package de.eorg.rollercoaster.client.exceptions;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class OutOfQuotaException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, de.eorg.rollercoaster.client.exceptions.OutOfQuotaException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static de.eorg.rollercoaster.client.exceptions.OutOfQuotaException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.eorg.rollercoaster.client.exceptions.OutOfQuotaException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.eorg.rollercoaster.client.exceptions.OutOfQuotaException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.eorg.rollercoaster.client.exceptions.OutOfQuotaException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.eorg.rollercoaster.client.exceptions.OutOfQuotaException_FieldSerializer.deserialize(reader, (de.eorg.rollercoaster.client.exceptions.OutOfQuotaException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.eorg.rollercoaster.client.exceptions.OutOfQuotaException_FieldSerializer.serialize(writer, (de.eorg.rollercoaster.client.exceptions.OutOfQuotaException)object);
  }
  
}
