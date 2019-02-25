package com.kabryxis.kabutils.data.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A {@link FilenameFilter} implementation that checks the ending of the file name for a specified string.
 * 
 * @author Kabryxis
 *
 */
public class FileEndingFilter implements FilenameFilter {
	
	private final String[] extensions;
	
	/**
	 * @param extension The file extension string (ex: ".txt")
	 */
	public FileEndingFilter(String extension) {
		extensions = new String[] { extension };
	}
	
	/**
	 * @param extensions The file extension strings (ex: ".mkv", ".mp4")
	 */
	public FileEndingFilter(String... extensions) {
		this.extensions = extensions;
	}
	
	@Override
	public boolean accept(File file, String name) {
		for(String extension : extensions) {
			if(name.endsWith(extension)) return true;
		}
		return false;
	}
	
}
