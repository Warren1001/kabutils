package com.kabryxis.kabutils.data.file.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

public class Config extends ConfigSection {
	
	private final static DumperOptions options = new DumperOptions();
	private final static Yaml yaml;
	
	static {
		options.setDefaultFlowStyle(FlowStyle.BLOCK);
		yaml = new Yaml(options);
	}
	
	private final File file;
	
	public Config(String name) {
		this.file = new File(name + ".yml");
		load();
	}
	
	public void load() {
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
		if(!file.exists()) {
			try {
				file.createNewFile();
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			FileWriter writer = new FileWriter(file);
			yaml.dump(saveToMap(), writer);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean exists() {
		return file.exists();
	}
	
}
