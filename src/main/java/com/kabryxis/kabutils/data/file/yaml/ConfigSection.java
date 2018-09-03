package com.kabryxis.kabutils.data.file.yaml;

import org.bukkit.util.NumberConversions;

import java.util.*;

public class ConfigSection {
	
	public static final String PERIOD = "\\.";
	private static final Map<Class<?>, Class<?>[]> castableClasses = new HashMap<>();
	
	static {
		castableClasses.put(Float.class, new Class<?>[] {Integer.class, Double.class});
		castableClasses.put(Byte.class, new Class<?>[] {Integer.class});
		castableClasses.put(Long.class, new Class<?>[] {Integer.class});
	}
	
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
	
	public ConfigSection getChild(String path, boolean create) {
		ConfigSection correctSection = getCorrectSection(path, create);
		return correctSection != null ? correctSection.getChild0(getCorrectKey(path), create) : null;
	}
	
	public ConfigSection getChild(String path) {
		return getChild(path, false);
	}
	
	public Set<ConfigSection> getChildren() {
		Collection<ConfigSection> values = children.values();
		Set<ConfigSection> sections = new HashSet<>(values.size());
		sections.addAll(values);
		return sections;
	}
	
	public Map<String, Object> getPairs(boolean deep) {
		Map<String, Object> map = new HashMap<>(data);
		if(deep) children.forEach((key, section) -> map.put(key, section.getPairs(true)));
		return map;
	}
	
	public Set<String> getKeys(boolean deep) {
		Set<String> keys = new HashSet<>(data.keySet());
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
	
	public Set<Object> getValues(boolean deep) {
		Set<Object> objects = new HashSet<>(data.values());
		if(deep) children.values().forEach(section -> addValuesDeep(objects));
		return objects;
	}
	
	private void addValuesDeep(Set<Object> objects) {
		objects.addAll(data.values());
		children.values().forEach(section -> section.addValuesDeep(objects));
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
		if(obj instanceof Number) {
			if(clazz == Float.class || clazz == float.class) obj = NumberConversions.toFloat(obj);
			else if(clazz == Byte.class || clazz == byte.class) obj = NumberConversions.toByte(obj);
			else if(clazz == Long.class || clazz == long.class) obj = NumberConversions.toLong(obj);
			else if(clazz == Short.class || clazz == short.class) obj = NumberConversions.toShort(obj);
			else if(clazz == Integer.class || clazz == int.class) obj = NumberConversions.toInt(obj);
			else if(clazz == Double.class || clazz == double.class) obj = NumberConversions.toDouble(obj);
		}
		return clazz.isInstance(obj) ? (T)obj : null;
	}
	
	public <T> T get(String path, Class<T> clazz, T def) {
		T obj = get(path, clazz);
		return obj != null ? obj : def;
	}
	
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
		data.remove(key);
		children.put(key, child);
	}
	
	protected ConfigSection getChild0(String key, boolean create) {
		return create ? children.computeIfAbsent(key, ConfigSection::new) : children.get(key);
	}
	
	protected void set0(String key, Object value) {
		children.remove(key);
		data.put(key, value);
	}
	
	protected Object get0(String path) {
		return data.get(path);
	}
	
	protected void remove0(String path) {
		data.remove(path);
		children.remove(path);
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
	
	@Override
	public String toString() {
		return saveToMap().toString();
	}
	
}
