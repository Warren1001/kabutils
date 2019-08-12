package com.kabryxis.kabutils.data.file;

import com.kabryxis.kabutils.data.Arrays;
import com.kabryxis.kabutils.string.Strings;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class KFiles {
	
	public static void forEachFileWithEnding(File folder, String ending, Consumer<? super File> action) {
		Arrays.forEach(folder.listFiles(new FileEndingFilter(ending)), action);
	}
	
	public static String getSimpleName(File file) {
		String fileName = file.getName();
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}
	
	public static String getExtension(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	public static File getFileWithPossibleEndings(File folder, String name, String... endings) {
		return Objects.requireNonNull(folder.listFiles(file -> getSimpleName(file).equalsIgnoreCase(name) &&
				Stream.of(endings).anyMatch(ending -> file.getName().toLowerCase().endsWith(ending.toLowerCase()))))[0];
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
	
	public static int countWords(File file) {
		Validate.isTrue(file != null && file.exists(), "file is null or does not exist");
		try {
			return Strings.split(String.join(" ", java.nio.file.Files.readAllLines(file.toPath())).trim(), " ").length;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
