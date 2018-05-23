package com.kabryxis.kabutils.spigot.data;

import com.kabryxis.kabutils.data.Data;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Config extends YamlConfiguration {
	
	private final static Map<File, Config> configs = new HashMap<>();
	
	public static Config get(File file) {
		return configs.computeIfAbsent(file, Config::new);
	}
	
	private File file;
	private String name;
	
	private Config(File file) {
		this.file = file;
		this.name = file.getName().split("\\.")[0];
	}
	
	public Config() {
		this.file = null;
		this.name = null;
	}
	
	@Override
	public String getName() {
		return name;
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
