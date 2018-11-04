package com.kabryxis.kabutils.string;

import java.util.regex.Pattern;

public class Strings {
	
	public static String[] split(String toSplit, String splitter) {
		return toSplit.contains(splitter) ? toSplit.split(Pattern.quote(splitter)) : new String[] { toSplit };
	}
	
	public static String alphaNumeric(String string) {
		return string.replaceAll("[^A-Za-z0-9]", "");
	}
	
	public static boolean contains(String string, char c) {
		return string.indexOf(c) != -1;
	}
	
	public static int count(String string, char c) {
		int count = 0;
		for(char ch : string.toCharArray()) {
			if(ch == c) count++;
		}
		return count;
	}
	
}
