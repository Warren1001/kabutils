package com.kabryxis.kabutils.data;

public class Classes {
	
	public static Class<?> getWrapperClass(Class<?> primClass) {
		if(primClass == int.class) return Integer.class;
		if(primClass == double.class) return Double.class;
		if(primClass == float.class) return Float.class;
		if(primClass == short.class) return Short.class;
		if(primClass == boolean.class) return Boolean.class;
		if(primClass == long.class) return Long.class;
		if(primClass == byte.class) return Byte.class;
		return primClass;
	}
	
}
