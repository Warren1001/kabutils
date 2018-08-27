package com.kabryxis.kabutils.spigot.version.custom;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.utility.ReflectionHelper;

import java.util.Map;

@SuppressWarnings("unchecked")
public class CustomEntityRegistry {
	
	private static final Map<String, Class<?>> classByName;
	private static final Map<Class<?>, String> nameByClass;
	private static final Map<Class<?>, Integer> idByClass;
	
	static {
		Class<?> entityTypesClass = Version.getNMSClass("EntityTypes");
		classByName = (Map<String, Class<?>>)ReflectionHelper.getObjectInStaticField(ReflectionHelper.getField(entityTypesClass, "c"));
		nameByClass = (Map<Class<?>, String>)ReflectionHelper.getObjectInStaticField(ReflectionHelper.getField(entityTypesClass, "d"));
		idByClass = (Map<Class<?>, Integer>)ReflectionHelper.getObjectInStaticField(ReflectionHelper.getField(entityTypesClass, "f"));
	}
	
	public static void registerEntity(String entityName, Class<?> entityClass) {
		Class<?> oldClass = classByName.get(entityName);
		classByName.put(entityName, entityClass);
		nameByClass.put(entityClass, entityName);
		idByClass.put(entityClass, idByClass.get(oldClass));
	}
	
}
