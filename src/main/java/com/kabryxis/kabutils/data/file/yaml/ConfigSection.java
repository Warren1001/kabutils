package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Maps;
import org.bukkit.util.NumberConversions;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConfigSection extends HashMap<String, Object> {
	
	public static final String PERIOD = "\\.";
	public static final Map<Class<?>, Function<String, ?>> DESERIALIZERS = new HashMap<>();
	
	private String name;
	
	public ConfigSection() {}
	
	public ConfigSection(Map<String, Object> map) {
		putAll(map);
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void putAll(Map<? extends String, ?> m) {
		m.forEach(this::put);
	}
	
	public Map<String, Object> getValues(boolean deep) {
		Map<String, Object> map = new HashMap<>(this);
		if(deep) {
			for(Entry<String, Object> entry : map.entrySet()) {
				Object value = entry.getValue();
				if(value instanceof ConfigSection) map.put(entry.getKey(), ((ConfigSection)value).getValues(true));
			}
		}
		return map;
	}
	
	public Set<String> getKeys(boolean deep) {
		Set<String> keys = new HashSet<>(keySet());
		if(deep) entrySet().stream().filter(entry -> entry.getValue() instanceof ConfigSection).forEach(entry -> ((ConfigSection)entry.getValue()).addKeysDeep(keys, entry.getKey()));
		return keys;
	}
	
	protected void addKeysDeep(Set<String> keys, String append) {
		keySet().forEach(key -> keys.add(append + "." + key));
		entrySet().stream().filter(entry -> entry.getValue() instanceof ConfigSection).forEach(entry -> ((ConfigSection)entry.getValue()).addKeysDeep(keys, append + "." + entry.getKey()));
	}
	
	@Override
	public Object put(String path, Object value) {
		return getCorrectSection(path, true).put0(getCorrectKey(path),
				value instanceof Map && !(value instanceof ConfigSection) ? new ConfigSection(Maps.convertMap((Map<?, ?>)value, Object::toString, o -> o)) : value);
	}
	
	@Override
	public Object remove(Object objPath) {
		String path = objPath.toString();
		ConfigSection section = getCorrectSection(path, false);
		return section == null ? null : section.remove0(getCorrectKey(path));
	}
	
	@Override
	public Object get(Object objPath) {
		return get(objPath.toString());
	}
	
	public Object get(String path) {
		ConfigSection section = getCorrectSection(path, false);
		return section == null ? null : section.get0(getCorrectKey(path));
	}
	
	public Object get(String path, Object def) {
		Object obj = get(path);
		return obj == null ? def : obj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String path, Class<T> clazz) {
		Object obj = get(path);
		if(obj == null) return null;
		if(obj instanceof Number) {
			if(clazz == Float.class || clazz == float.class) obj = NumberConversions.toFloat(obj);
			else if(clazz == Byte.class || clazz == byte.class) obj = NumberConversions.toByte(obj);
			else if(clazz == Long.class || clazz == long.class) obj = NumberConversions.toLong(obj);
			else if(clazz == Short.class || clazz == short.class) obj = NumberConversions.toShort(obj);
			else if(clazz == Integer.class || clazz == int.class) obj = NumberConversions.toInt(obj);
			else if(clazz == Double.class || clazz == double.class) obj = NumberConversions.toDouble(obj);
		}
		else if(obj instanceof String) {
			if(clazz.isEnum()) obj = Enum.valueOf((Class<? extends Enum>)clazz, ((String)obj).toUpperCase().replace(' ', '_'));
			else if(DESERIALIZERS.containsKey(clazz)) obj = DESERIALIZERS.get(clazz).apply((String)obj);
		}
		return obj instanceof Number || clazz.isInstance(obj) ? (T)obj : null;
	}
	
	public <T> T get(String path, Class<T> clazz, T def) {
		T obj = get(path, clazz);
		return obj == null ? def : obj;
	}
	
	public <T> T get(String path, Class<T> clazz, T def, boolean setDefault) {
		T obj = get(path, clazz);
		if(obj == null) {
			if(setDefault) put(path, def);
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
	
	protected Object put0(String key, Object value) {
		if(value instanceof ConfigSection) ((ConfigSection)value).setName(key);
		return super.put(key, value);
	}
	
	protected Object get0(String path) {
		return super.get(path);
	}
	
	protected Object remove0(String path) {
		Object obj = super.remove(path);
		if(obj instanceof ConfigSection) ((ConfigSection)obj).setName(null);
		return obj;
	}
	
	protected ConfigSection getCorrectSection(String path, boolean create) {
		ConfigSection section = this;
		if(path.contains(".")) {
			String[] args = path.split(PERIOD);
			for(int i = 0; i < args.length - 1; i++) {
				ConfigSection newSection = section.get(args[i], ConfigSection.class);
				if(newSection == null && create) section.put(args[1], (newSection = new ConfigSection()));
				if(newSection == null) return null;
				section = newSection;
			}
		}
		return section;
	}
	
	protected String getCorrectKey(String path) {
		return path.contains(".") ? path.substring(path.lastIndexOf(".") + 1) : path;
	}
	
	public Set<ConfigSection> getChildren() {
		return values().stream().filter(ConfigSection.class::isInstance).map(ConfigSection.class::cast).collect(Collectors.toSet());
	}
	
}
