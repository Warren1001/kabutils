package com.kabryxis.kabutils.data;

public class Arrays {
	
	public static boolean contains(Object[] array, Object o) {
		for(Object a : array) {
			if(a == o) return true;
		}
		return false;
	}
	
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
	
}
