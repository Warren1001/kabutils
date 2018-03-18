package com.kabryxis.kabutils.data;

public class Arrays {
	
	public static boolean containsInt(int[] array, int i) {
		for(int a : array) {
			if(a == i) return true;
		}
		return false;
	}
	
	public static String[] splitStringArray(int startingIndex, String[] originalArray) {
		String[] newArray = new String[originalArray.length - startingIndex];
		System.arraycopy(originalArray, startingIndex, newArray, 0, originalArray.length - startingIndex);
		return newArray;
	}
	
	public static boolean containsClass(Class<?>[] array, Class<?> clazz) {
		for(Class<?> c : array) {
			if(c == clazz) return true;
		}
		return false;
	}
	
}
