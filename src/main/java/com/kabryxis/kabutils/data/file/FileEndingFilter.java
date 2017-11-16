package com.kabryxis.kabutils.data.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A {@link FilenameFilter} implementation that checks the ending of the file name for a specific extension.
 * 
 * @author Kabryxis
 *
 */
public class FileEndingFilter implements FilenameFilter {
	
	private final String fileExtension;
	
	/**
	 * @param fileExtension The file extension string (ex: ".txt")
	 */
	public FileEndingFilter(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	@Override
	public boolean accept(File file, String name) {
		return name.endsWith(fileExtension);
	}
	
}
