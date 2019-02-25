package com.kabryxis.kabutils.data.file;

import com.kabryxis.kabutils.data.Arrays;

import java.io.File;
import java.util.function.Consumer;

public class Files {
	
	public static void forEachFileWithEnding(File folder, String ending, Consumer<? super File> action) {
		File[] files = folder.listFiles(new FileEndingFilter(ending));
		if(files != null && files.length > 0) {
			for(File file : files) {
				action.accept(file);
			}
		}
	}
	
	public static String getSimpleName(File file) {
		String fileName = file.getName();
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}
	
	public static File[] getFilesWithEnding(File folder, String ending) {
		return folder.listFiles(new FileEndingFilter(ending));
	}
	
	public static File[] getFilesWithEndings(File folder, String... endings) {
		return folder.listFiles(new FileEndingFilter(endings));
	}
	
	public static File[] getDirectories(File folder) {
		return folder.listFiles(File::isDirectory);
	}
	
	public static void forEachDirectory(File folder, Consumer<? super File> action) {
		Arrays.forEach(getDirectories(folder), action);
	}
	
}
