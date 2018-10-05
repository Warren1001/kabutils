package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Data;
import com.kabryxis.kabutils.data.Maps;
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
		yaml = new Yaml(new KabYamlConstructor(), new KabYamlRepresenter(), options);
	}
	
	private final File file;
	private final String name;
	
	public Config(File file) {
		this.file = file;
		this.name = file.getName().split("\\.")[0];
	}
	
	public void load() {
		if(exists()) Data.queue(this::loadSync);
	}
	
	public void load(Consumer<Config> callable) {
		if(exists()) {
			Data.queue(() -> {
				loadSync();
				callable.accept(this);
			});
		}
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
		if(obj instanceof ConfigSection) {
			putAll((ConfigSection)obj);
		}
		else if(obj instanceof Map) putAll(Maps.convertMap((Map<?, ?>)obj, Object::toString, o -> o));
		else throw new IllegalArgumentException(getClass().getSimpleName() + " does not know how to load " + (obj == null ? "null" : obj.getClass().getSimpleName()) + " object from yaml");
	}
	
	public void save() {
		Data.queue(this::saveSync);
	}
	
	public void saveSync() {
		try(FileWriter writer = new FileWriter(file)) {
			yaml.dump(this, writer);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean exists() {
		return file != null && file.exists();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
