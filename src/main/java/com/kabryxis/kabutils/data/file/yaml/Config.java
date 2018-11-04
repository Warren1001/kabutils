package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Maps;
import com.kabryxis.kabutils.data.file.Files;
import com.kabryxis.kabutils.data.file.yaml.serialization.Serializer;
import org.apache.commons.lang3.Validate;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Config extends ConfigSection {
	
	public static final String CUSTOM_INDICATOR = "==";
	public static final String EXTENSION = ".yml";
	private static final KabYamlConstructor YAML_CONSTRUCTOR = new KabYamlConstructor();
	private static final KabYamlRepresenter YAML_REPRESENTER = new KabYamlRepresenter();
	public static final DumperOptions OPTIONS = new DumperOptions();
	public static final Yaml YAML_INSTANCE;
	
	static {
		OPTIONS.setDefaultFlowStyle(FlowStyle.BLOCK);
		YAML_INSTANCE = new Yaml(YAML_CONSTRUCTOR, YAML_REPRESENTER, OPTIONS);
	}
	
	public static void registerSerializer(Serializer serializer) {
		YAML_CONSTRUCTOR.registerSerializer(serializer);
		YAML_REPRESENTER.registerSerializer(serializer);
	}
	
	private final File file;
	private final String name;
	
	public Config(File file, boolean load) {
		this.file = Validate.notNull(file, "file cannot be null");
		this.name = Files.getSimpleName(file);
		if(load) load();
	}
	
	public Config(File file) {
		this(file, false);
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public Config load() {
		if(!exists()) return this;
		Object obj;
		try(FileInputStream fis = new FileInputStream(file)) {
			obj = YAML_INSTANCE.load(fis);
		} catch(IOException e) {
			e.printStackTrace();
			return this;
		}
		if(obj instanceof ConfigSection) putAll((ConfigSection)obj);
		else if(obj instanceof Map) putAll(Maps.convert((Map<?, ?>)obj, Object::toString, o -> o));
		else throw new IllegalArgumentException(getClass().getSimpleName() + " does not know how to load " + (obj == null ? "null" : obj.getClass().getName()) + " object from yaml");
		return this;
	}
	
	public <T> T load(Class<T> clazz) {
		return load(clazz, true);
	}
	
	public <T> T load(Class<T> clazz, boolean loadConfigIfDefault) {
		if(exists()) {
			Object obj;
			try(FileInputStream fis = new FileInputStream(file)) {
				obj = YAML_INSTANCE.load(fis);
			} catch(IOException e) {
				e.printStackTrace();
				return null;
			}
			if(clazz.isInstance(obj)) return clazz.cast(obj);
			if(loadConfigIfDefault) {
				if(obj instanceof ConfigSection) putAll((ConfigSection)obj);
				else if(obj instanceof Map) putAll(Maps.convert((Map<?, ?>)obj, Object::toString, o -> o));
			}
		}
		return null;
	}
	
	public void save() {
		try(FileWriter writer = new FileWriter(file)) {
			YAML_INSTANCE.dump(this, writer);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	@Override
	public Config builderPut(String path, Object value) {
		put(path, value);
		return this;
	}
	
}
