package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Lists;
import com.kabryxis.kabutils.data.Maps;
import com.kabryxis.kabutils.data.NumberConversions;
import com.kabryxis.kabutils.data.Objects;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConfigSection extends HashMap<String, Object> implements SetListener, RemovedListener {
	
	public static final String PERIOD = "\\.";
	private static final Map<Class<?>, Function<String, ?>> DESERIALIZERS = new HashMap<>();
	
	public static <T> void addDeserializer(Class<T> clazz, Function<String, T> function) {
		DESERIALIZERS.put(clazz, function);
	}
	
	private String name;
	
	public ConfigSection() {}
	
	public ConfigSection(Map<String, Object> map) {
		putAll(map);
	}
	
	@Nullable
	public String getName() {
		return name;
	}
	
	@Override
	public void putAll(Map<? extends String, ?> map) {
		map.forEach(this::put);
	}
	
	public void putAll(ConfigSection section) {
		super.putAll(section);
	}
	
	public Map<String, Object> getDeepValues() {
		Map<String, Object> map = new HashMap<>(this);
		for(Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if(value instanceof ConfigSection) map.put(entry.getKey(), ((ConfigSection)value).getDeepValues());
		}
		return map;
	}
	
	public Collection<String> getDeepKeys() {
		Set<String> keys = new HashSet<>(keySet());
		getChildren().forEach(child -> child.addKeysDeep(keys, child.getName()));
		return keys;
	}
	
	protected void addKeysDeep(Set<String> keys, String append) {
		keySet().forEach(key -> keys.add(append + "." + key));
		getChildren().forEach(child -> child.addKeysDeep(keys, append + "." + child.getName()));
	}
	
	@Override
	public Object put(String path, Object value) {
		return getCorrectSection(path, true).put0(getCorrectKey(path),
				value instanceof Map && !(value instanceof ConfigSection) ? new ConfigSection(Maps.convert((Map<?, ?>)value, Object::toString, o -> o)) : value);
	}
	
	public ConfigSection builderPut(String path, Object value) {
		put(path, value);
		return this;
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
	
	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		ConfigSection section = getCorrectSection(path, false);
		if(section == null) return null;
		Object obj = section.get0(getCorrectKey(path));
		try {
			return (T)obj;
		} catch(ClassCastException ignore) {
			return null;
		}
	}
	
	public <T> T get(String path, T def) {
		return Objects.getFirstNonnull(get(path), def);
	}
	
	public <T> T computeIfAbsent(String path, T def) {
		T t = get(path, def);
		if(t == def) put(path, t);
		return t;
	}
	
	public <T> T computeIfAbsent(String path, Function<? super String, T> mappingFunction) {
		T t = get(path);
		if(t == null) {
			t = mappingFunction.apply(getCorrectKey(path));
			put(path, t);
		}
		return t;
	}
	
	public <T> T get(String path, Class<T> clazz) {
		Object obj = get(path);
		return clazz.isInstance(obj) ? clazz.cast(obj) : null;
	}
	
	public <T> T get(String path, Class<T> clazz, T def) {
		return Objects.getFirstNonnull(get(path, clazz), def);
	}
	
	public <T> T computeIfAbsent(String path, Class<T> clazz, T def) {
		T t = get(path, clazz, def);
		if(t == def) put(path, t);
		return t;
	}
	
	public int getInt(String path) {
		return NumberConversions.toInt(get(path));
	}
	
	public float getFloat(String path) {
		return NumberConversions.toFloat(get(path));
	}
	
	public double getDouble(String path) {
		return NumberConversions.toDouble(get(path));
	}
	
	public short getShort(String path) {
		return NumberConversions.toShort(get(path));
	}
	
	public long getLong(String path) {
		return NumberConversions.toLong(get(path));
	}
	
	public byte getByte(String path) {
		return NumberConversions.toByte(get(path));
	}
	
	public boolean getBoolean(String path) {
		Boolean bool = get(path);
		return bool != null && bool;
	}
	
	public int getInt(String path, int def) {
		return NumberConversions.toInt(get(path), def);
	}
	
	public float getFloat(String path, float def) {
		return NumberConversions.toFloat(get(path), def);
	}
	
	public double getDouble(String path, double def) {
		return NumberConversions.toDouble(get(path), def);
	}
	
	public short getShort(String path, short def) {
		return NumberConversions.toShort(get(path), def);
	}
	
	public long getLong(String path, long def) {
		return NumberConversions.toLong(get(path), def);
	}
	
	public byte getByte(String path, byte def) {
		return NumberConversions.toByte(get(path), def);
	}
	
	public boolean getBoolean(String path, boolean def) {
		Boolean bool = get(path);
		return bool == null ? def : bool;
	}
	
	public int computeIntIfAbsent(String path, int def) {
		int i = getInt(path, def);
		if(i == def) put(path, i);
		return i;
	}
	
	public float computeFloatIfAbsent(String path, float def) {
		float f = getFloat(path, def);
		if(f == def) put(path, f);
		return f;
	}
	
	public double computeDoubleIfAbsent(String path, double def) {
		double d = getDouble(path, def);
		if(d == def) put(path, d);
		return d;
	}
	
	public short computeShortIfAbsent(String path, short def) {
		short s = getShort(path, def);
		if(s == def) put(path, s);
		return s;
	}
	
	public long computeLongIfAbsent(String path, long def) {
		long l = getLong(path, def);
		if(l == def) put(path, l);
		return l;
	}
	
	public byte computeByteIfAbsent(String path, byte def) {
		byte b = getByte(path, def);
		if(b == def) put(path, b);
		return b;
	}
	
	public boolean computeBooleanIfAbsent(String path, boolean def) {
		Boolean bool = get(path);
		if(bool != null) return bool;
		put(path, def);
		return def;
	}
	
	public ConfigSection computeSectionIfAbsent(String path) {
		return computeIfAbsent(path, ignore -> new ConfigSection());
	}
	
	/*@SuppressWarnings("unchecked")
	public <T> T get(String path, Class<T> clazz) {
		Object obj = get(path);
		if(obj == null) return null;
		boolean forceCast = false;
		if(obj instanceof Number) {
			if(clazz == Float.class || clazz == float.class) {
				obj = NumberConversions.toFloat(obj);
				forceCast = true;
			}
			else if(clazz == Byte.class || clazz == byte.class) {
				obj = NumberConversions.toByte(obj);
				forceCast = true;
			}
			else if(clazz == Long.class || clazz == long.class) {
				obj = NumberConversions.toLong(obj);
				forceCast = true;
			}
			else if(clazz == Short.class || clazz == short.class) {
				obj = NumberConversions.toShort(obj);
				forceCast = true;
			}
			else if(clazz == Integer.class || clazz == int.class) {
				obj = NumberConversions.toInt(obj);
				forceCast = true;
			}
			else if(clazz == Double.class || clazz == double.class) {
				obj = NumberConversions.toDouble(obj);
				forceCast = true;
			}
		}
		else if(obj instanceof String) {
			if(clazz.isEnum()) obj = Enum.valueOf((Class<? extends Enum>)clazz, obj.toString().toUpperCase().replace(' ', '_'));
			else if(DESERIALIZERS.containsKey(clazz)) obj = DESERIALIZERS.get(clazz).apply(obj.toString());
		}
		return forceCast || clazz.isInstance(obj) ? (T)obj : null;
	}
	
	public <T> T get(String path, Class<T> clazz, T def) {
		return Objects.getFirstNonnull(get(path, clazz), def);
	}
	
	public <T> T computeIfAbsent(String path, Class<T> clazz, T def) {
		T t = get(path, clazz, def);
		if(t == def) put(path, t);
		return t;
	}*/
	
	public <T extends Enum<T>> T getEnum(String path, Class<T> clazz) {
		String string = get(path);
		return string == null ? null : Enum.valueOf(clazz, string.toUpperCase().replace(' ', '_'));
	}
	
	public <T extends Enum<T>> T getEnum(String path, Class<T> clazz, T def) {
		return Objects.getFirstNonnull(getEnum(path, clazz), def);
	}
	
	public <T extends Enum<T>> T computeEnumIfAbsent(String path, Class<T> clazz, T def) {
		T t = getEnum(path, clazz, def);
		if(t == def) put(path, t);
		return t;
	}
	
	public <T> T getCustom(String path, Class<T> clazz) {
		String string = get(path);
		return string == null ? null : (DESERIALIZERS.containsKey(clazz) ? clazz.cast(DESERIALIZERS.get(clazz).apply(string)) : null);
	}
	
	public <T> T getCustom(String path, Class<T> clazz, T def) {
		return Objects.getFirstNonnull(getCustom(path, clazz), def);
	}
	
	public <T> T computeCustomIfAbsent(String path, Class<T> clazz, T def) {
		T t = getCustom(path, clazz, def);
		if(t == def) put(path, t);
		return t;
	}
	
	public <T> List<T> getList(String path, Class<T> clazz) {
		Object obj = get(path);
		return obj instanceof List ? Lists.convert((List<?>)obj, clazz) : null;
	}
	
	public <T> List<T> getList(String path, Function<Object, T> converter) {
		Object obj = get(path);
		return obj instanceof List ? Lists.convert((List<?>)obj, converter) : null;
	}
	
	public <T> List<T> getList(String path, Class<T> clazz, Function<Object, T> converter) {
		Object obj = get(path);
		return obj instanceof List ? Lists.convert((List<?>)obj, clazz, converter) : null;
	}
	
	public <T> List<T> getList(String path, Class<T> clazz, List<T> def) {
		return Objects.getFirstNonnull(getList(path, clazz), def);
	}
	
	public <T> List<T> getList(String path, Function<Object, T> converter, List<T> def) {
		return Objects.getFirstNonnull(getList(path, converter), def);
	}
	
	public <T> List<T> getList(String path, Class<T> clazz, Function<Object, T> converter, List<T> def) {
		return Objects.getFirstNonnull(getList(path, clazz, converter), def);
	}
	
	public <T> List<T> computeListIfAbsent(String path, Class<T> clazz, List<T> def) {
		List<T> list = getList(path, clazz, def);
		if(list == def) put(path, list);
		return list;
	}
	
	public <T> List<T> computeListIfAbsent(String path, Function<Object, T> converter, List<T> def) {
		List<T> list = getList(path, converter, def);
		if(list == def) put(path, list);
		return list;
	}
	
	public <T> List<T> computeListIfAbsent(String path, Class<T> clazz, Function<Object, T> converter, List<T> def) {
		List<T> list = getList(path, clazz, converter, def);
		if(list == def) put(path, list);
		return list;
	}
	
	@Override
	public void set(ConfigSection section, String key) {
		if(name == null) name = key;
	}
	
	@Override
	public void removed(ConfigSection section, String key) {
		name = null;
	}
	
	protected Object put0(String key, Object value) {
		if(value instanceof SetListener) ((SetListener)value).set(this, key);
		return super.put(key, value);
	}
	
	protected Object get0(String key) {
		return super.get(key);
	}
	
	protected Object remove0(String key) {
		Object obj = super.remove(key);
		if(obj instanceof RemovedListener) ((RemovedListener)obj).removed(this, key);
		return obj;
	}
	
	protected ConfigSection getCorrectSection(String path, boolean create) {
		ConfigSection section = this;
		if(path.contains(".")) {
			String[] args = path.split(PERIOD);
			for(int i = 0; i < args.length - 1; i++) {
				ConfigSection newSection = section.get(args[i]);
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
	
	public Collection<ConfigSection> getChildren() {
		return values().stream().filter(ConfigSection.class::isInstance).map(ConfigSection.class::cast).collect(Collectors.toSet());
	}
	
}
