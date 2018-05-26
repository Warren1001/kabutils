package com.kabryxis.kabutils.spigot.data;

import com.kabryxis.kabutils.data.Data;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.function.Consumer;

public class Config extends YamlConfiguration {
	
	private File file;
	private String name;
	
	public Config(File file) {
		this.file = file;
		this.name = file.getName().split("\\.")[0];
	}
	
	public Config(File file, InputStream is) {
		this.file = file;
		try(InputStreamReader isr = new InputStreamReader(is)) {
			load(isr);
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Config() {
		this.file = null;
		this.name = null;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void load() {
		if(file != null && file.exists()) Data.queue(this::load0);
	}
	
	public void load(Consumer<Config> callable) {
		if(file != null && file.exists()) Data.queue(() -> {
			load0();
			callable.accept(this);
		});
	}
	
	public void save() {
		if(file != null) Data.queue(this::save0);
	}
	
	private void load0() {
		try {
			load(file);
		}
		catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void save0() {
		try {
			save(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
