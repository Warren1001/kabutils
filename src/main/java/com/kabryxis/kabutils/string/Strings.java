package com.kabryxis.kabutils.string;

/**
 * A utility class with helpful and useful methods pertaining to {@link String} manipulation.
 * 
 * @author Kabryxis
 *
 */
public class Strings {
	
	/**
	 * Splits a {@link String}. If it cannot split, it simply returns an array containing the {@link String} attempting to be split.
	 * Will have weird behavior if regex is involved. If regex is involed, use {@link String#split(String)}.
	 * 
	 * @param toSplit The {@link String} to split.
	 * @param splitter The instances to split the above {@link String} at.
	 * @return A {@link String} array split from the given {@link String}, or one {@link String} if it was unable to be split.
	 */
	public static String[] split(String toSplit, String splitter) {
		return toSplit.contains(splitter) ? toSplit.split(splitter) : new String[] { toSplit };
	}
	
	public static String alphaNumeric(String string) {
		return string.replaceAll("[^A-Za-z0-9]", "");
	}
	
}
