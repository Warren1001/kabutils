package com.kabryxis.kabutils.data.file.yaml.serialization;

public class SerializerException extends RuntimeException {
	
	public SerializerException(String message, Object... objs) {
		super(String.format(message, objs));
	}
	
	public SerializerException(Exception exception, String message, Object... objs) {
		super(String.format(message, objs), exception);
	}
	
}
