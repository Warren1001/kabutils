package com.kabryxis.kabutils.data.file.yaml;

import java.util.*;

public class ConfigSection {
	
	public final static String PERIOD = "\\.";
	
	protected final Map<String, Object> data = new HashMap<>();
	protected final Map<String, ConfigSection> children = new HashMap<>();
	protected final String name;
	
	public ConfigSection() {
		this("root");
	}
	
	public ConfigSection(String name) {
		this.name = name;
	}
	
	public ConfigSection(String name, Map<?, ?> map) {
		this(name);
		load(map);
	}
	
	protected void load(Map<?, ?> map) {
		data.clear();
		children.clear();
		map.forEach((key, value) -> set(key.toString(), value));
	}
	
	public String getName() {
		return name;
	}
	
	public ConfigSection getChild(String name, boolean create) {
		return create ? children.computeIfAbsent(name, ConfigSection::new) : children.get(name);
	}
	
	public ConfigSection getChild(String name) {
		return getChild(name, false);
	}
	
	public Set<ConfigSection> getChildren() {
		Collection<ConfigSection> values = children.values();
		Set<ConfigSection> sections = new HashSet<>(values.size());
		sections.addAll(values);
		return sections;
	}
	
	public Set<String> getKeys(boolean deep) {
		Set<String> keys = new HashSet<>(data.size());
		keys.addAll(data.keySet());
		keys.addAll(children.keySet());
		if(deep) children.values().forEach(section -> section.addKeysDeep(keys, section.getName()));
		return keys;
	}
	
	private void addKeysDeep(Set<String> keys, String append) {
		for(String key : data.keySet()) {
			keys.add(append + "." + key);
		}
		children.values().forEach(section -> section.addKeysDeep(keys, append + "." + section.getName()));
	}
	
	public void set(String path, Object value) {
		ConfigSection section = getCorrectSection(path, true);
		String key = getCorrectKey(path);
		if(value instanceof Map) section.setChild(key, new ConfigSection(key, (Map<?, ?>)value));
		else section.set0(key, value);
	}
	
	public void remove(String path) {
		ConfigSection section = getCorrectSection(path, false);
		if(section == null) return;
		section.remove0(getCorrectKey(path));
	}
	
	public Object get(String path) {
		ConfigSection section = getCorrectSection(path, false);
		if(section == null) return null;
		return section.get0(getCorrectKey(path));
	}
	
	public Object get(String path, Object def) {
		Object obj = get(path);
		return obj != null ? obj : def;
	}
	
	public <T> T get(String path, Class<T> clazz) {
		Object obj = get(path);
		return obj != null && clazz.isInstance(obj) ? clazz.cast(obj) : null;
	}
	
	public <T> T get(String path, Class<T> clazz, T def) {
		T obj = get(path, clazz);
		return obj != null ? obj : def;
	}
	
	/**
	 * Attempts to retrieve the value set at the specified path and cast it into the specified class.
	 * If no object is found there, it will return the provided default value.
	 * If setDefault is true and no object was found at the path, the default object provided will be set at the path.
	 * 
	 * @param path The path the object is set at.
	 * @param clazz The class of the object you are trying to get.
	 * @param def The default return value if an object is not found at the specified path.
	 * @param setDefault Whether to set the provided default value at the path if no object was found.
	 * @return
	 */
	public <T> T get(String path, Class<T> clazz, T def, boolean setDefault) {
		T obj = get(path, clazz);
		if(obj == null) {
			if(setDefault) set(path, def);
			return def;
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String path, Class<T> clazz) {
		Object obj = get(path);
		if(obj instanceof List) {
			List<?> list = (List<?>)obj;
			if(list.size() == 0) return new ArrayList<>();
			return clazz.isInstance(list.get(0)) ? (List<T>)list : null;
		}
		return null;
	}
	
	public <T> List<T> getList(String path, Class<T> clazz, List<T> def) {
		List<T> list = getList(path, clazz);
		return list != null ? list : def;
	}
	
	protected void setChild(String key, ConfigSection child) {
		children.put(key, child);
	}
	
	protected void set0(String key, Object value) {
		data.put(key, value);
	}
	
	protected Object get0(String path) {
		return data.get(path);
	}
	
	protected void remove0(String path) {
		data.remove(path);
	}
	
	protected Map<String, Object> saveToMap() {
		Map<String, Object> map = new HashMap<>();
		data.forEach(map::put);
		children.forEach((key, value) -> map.put(key, value.saveToMap()));
		return map;
	}
	
	protected ConfigSection getCorrectSection(String path, boolean create) {
		ConfigSection section = this;
		if(path.contains(".")) {
			String[] args = path.split(PERIOD);
			for(int i = 0; i < args.length - 1; i++) {
				section = section.getChild(args[i], create);
				if(section == null) return null;
			}
		}
		return section;
	}
	
	protected String getCorrectKey(String path) {
		return path.contains(".") ? path.substring(path.lastIndexOf(".") + 1) : path;
	}
	
}
