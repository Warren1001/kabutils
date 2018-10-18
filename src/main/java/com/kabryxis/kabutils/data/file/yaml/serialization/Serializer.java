package com.kabryxis.kabutils.data.file.yaml.serialization;

public interface Serializer<T> {
	
	String prefix();
	
	default Class<?>[] classes() {
		return null;
	}
	
	default T serialize(Object obj) {
		return null;
	}
	
	default Object deserialize(T obj) {
		return obj;
	}
	
}
