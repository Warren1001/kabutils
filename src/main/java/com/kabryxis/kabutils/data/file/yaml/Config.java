package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Data;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class Config extends ConfigSection {
	
	private final static DumperOptions options = new DumperOptions();
	private final static Yaml yaml;
	
	static {
		options.setDefaultFlowStyle(FlowStyle.BLOCK);
		yaml = new Yaml(options);
	}
	
	private final File file;
	private final String name;
	
	public Config(File file) {
		this.file = file;
		this.name = file.getName().split("\\.")[0];
	}
	
	public Config(String name) {
		this.file = new File(name);
		this.name = name;
	}
	
	public void load() {
		Data.queue(this::loadSync);
	}
	
	public void load(Consumer<Config> callable) {
		Data.queue(() -> {
			loadSync();
			callable.accept(this);
		});
	}
	
	public void loadSync() {
		Object obj;
		try {
			if(!file.exists()) return;
			obj = yaml.load(new FileInputStream(file));
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		if(obj == null) return;
		try {
			load((Map<?, ?>)obj);
		}
		catch(ClassCastException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		Data.queue(this::save0);
	}
	
	private void save0() {
		if(!file.exists()) {
			try {
				file.createNewFile();
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
		}
		try(FileWriter writer = new FileWriter(file)) {
			yaml.dump(saveToMap(), writer);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
