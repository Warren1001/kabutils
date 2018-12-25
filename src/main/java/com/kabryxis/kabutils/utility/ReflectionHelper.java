package com.kabryxis.kabutils.utility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	
	public static void setObjectInStaticField(Class<?> clazz, String fieldName, Object value) {
		setObjectInStaticField(getField(clazz, fieldName), value);
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
	
	public static Object getObjectInStaticField(Class<?> clazz, String fieldName) {
		return getObjectInStaticField(getField(clazz, fieldName));
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
	
	public static void setIntInStaticField(Class<?> clazz, String fieldName, int value) {
		setIntInStaticField(getField(clazz, fieldName), value);
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
	
	public static int getIntInStaticField(Class<?> clazz, String fieldName) {
		return getIntInStaticField(getField(clazz, fieldName));
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
	
	public static Object getObjectFromStaticGetter(Class<?> clazz, String methodName) {
		try {
			Method method = clazz.getDeclaredMethod(methodName);
			method.setAccessible(true);
			return method.invoke(null);
		} catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
}
