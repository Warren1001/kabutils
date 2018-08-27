package com.kabryxis.kabutils.data.file;

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
	
}
