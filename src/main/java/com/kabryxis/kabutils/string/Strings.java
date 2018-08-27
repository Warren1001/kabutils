package com.kabryxis.kabutils.string;

public class Strings {
	
	public static String[] split(String toSplit, String splitter) {
		return toSplit.contains(splitter) ? toSplit.split(splitter) : new String[] { toSplit };
	}
	
	public static String alphaNumeric(String string) {
		return string.replaceAll("[^A-Za-z0-9]", "");
	}
	
	public static boolean contains(String string, char c) {
		return string.indexOf(c) != -1;
	}
	
}
