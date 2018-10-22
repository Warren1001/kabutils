package com.kabryxis.kabutils.utility;

import java.lang.reflect.Field;

public class ReflectionHelper {
	
	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch(NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setObjectInStaticField(Field field, Object value) {
		setObjectInField(field, null, value);
	}
	
	public static void setObjectInField(Field field, Object instance, Object value) {
		try {
			field.set(instance, value);
		} catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Object getObjectInStaticField(Field field) {
		return getObjectInField(field, null);
	}
	
	public static Object getObjectInField(Field field, Object instance) {
		try {
			return field.get(instance);
		} catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setIntInStaticField(Field field, int value) {
		setIntInField(field, null, value);
	}
	
	public static void setIntInField(Field field, Object instance, int value) {
		try {
			field.setInt(instance, value);
		} catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int getIntInStaticField(Field field) {
		return getIntInField(field, null);
	}
	
	public static int getIntInField(Field field, Object instance) {
		try {
			return field.getInt(instance);
		} catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
}