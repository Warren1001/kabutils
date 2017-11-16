package com.kabryxis.kabutils.data;

public class Arrays {
	
	public static boolean containsInt(int[] array, int i) {
		for(int a : array) {
			if(a == i) return true;
		}
		return false;
	}
	
}
