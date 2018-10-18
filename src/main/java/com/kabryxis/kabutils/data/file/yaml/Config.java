package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Data;
import com.kabryxis.kabutils.data.Maps;
import com.kabryxis.kabutils.data.file.yaml.serialization.ConfigSectionSerializer;
import com.kabryxis.kabutils.data.file.yaml.serialization.MapSerializer;
import com.kabryxis.kabutils.data.file.yaml.serialization.StringSerializer;
import org.apache.commons.lang3.Validate;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

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
	
	public static void registerSerializer(StringSerializer serializer) {
		YAML_CONSTRUCTOR.registerSerializer(serializer);
		YAML_REPRESENTER.registerSerializer(serializer);
	}
	
	public static void registerSerializer(MapSerializer serializer) {
		YAML_CONSTRUCTOR.registerSerializer(serializer);
		YAML_REPRESENTER.registerSerializer(serializer);
	}
	
	public static void registerSerializer(ConfigSectionSerializer serializer) {
		YAML_CONSTRUCTOR.registerSerializer(serializer);
		YAML_REPRESENTER.registerSerializer(serializer);
	}
	
	private final File file;
	private final String name;
	
	public Config(File file, boolean load) {
		Validate.notNull(file, "file cannot be null");
		this.file = file;
		this.name = file.getName().split("\\.")[0];
		if(load) load();
	}
	
	public Config(File file) {
		this(file, false);
	}
	
	public File getFile() {
		return file;
	}
	
	@Nonnull
	@Override
	public String getName() {
		return name;
	}
	
	public void loadAsync() {
		if(exists()) Data.queue(this::load);
	}
	
	public void loadAsync(Consumer<Config> callable) {
		if(exists()) {
			Data.queue(() -> {
				load();
				callable.accept(this);
			});
		}
	}
	
	public void load() {
		if(!exists()) return;
		Object obj;
		try(FileInputStream fis = new FileInputStream(file)) {
			obj = YAML_INSTANCE.load(fis);
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
		if(obj instanceof ConfigSection) putAll((ConfigSection)obj);
		else if(obj instanceof Map) putAll(Maps.convert((Map<?, ?>)obj, Object::toString, o -> o));
		else throw new IllegalArgumentException(getClass().getSimpleName() + " does not know how to load " + (obj == null ? "null" : obj.getClass().getName()) + " object from yaml");
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
			if(loadConfigIfDefault) {
				if(clazz.isInstance(obj)) return clazz.cast(obj);
				if(obj instanceof ConfigSection) putAll((ConfigSection)obj);
				else if(obj instanceof Map) putAll(Maps.convert((Map<?, ?>)obj, Object::toString, o -> o));
			}
			else if(clazz.isInstance(obj)) return clazz.cast(obj);
		}
		return null;
	}
	
	public void saveAsync() {
		Data.queue(this::save);
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
	
	
}
