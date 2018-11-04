package com.kabryxis.kabutils.data.file.yaml.serialization;

public interface Serializer {
	
	String getPrefix();
	
	SerializationType getType();
	
	default Class<?>[] getClasses() {
		return null;
	}
	
	Object deserialize(Object obj);
	
	default Object serialize(Object obj) {
		return null;
	}
	
}
