package com.kabryxis.kabutils.spigot.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.kabryxis.kabutils.data.Data;

public class Config extends YamlConfiguration {
	
	private final static Map<File, Config> configs = new HashMap<>();
	
	public static Config get(File file) {
		return configs.computeIfAbsent(file, Config::new);
	}
	
	private final File file;
	private final String name;
	
	private Config(File file) {
		this.file = file;
		this.name = file.getName().split("\\.")[0];
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void load() {
		if(file.exists()) Data.queue(() -> {
			load0();
		});
	}
	
	public void load(Consumer<Config> callable) {
		if(file.exists()) Data.queue(() -> {
			load0();
			callable.accept(this);
		});
	}
	
	public void save() {
		Data.queue(() -> {
			save0();
		});
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
