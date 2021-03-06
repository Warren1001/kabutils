package com.kabryxis.kabutils.spigot.version.custom;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.utility.ReflectionHelper;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CustomEntityRegistry {
	
	private static final Map<String, Class<?>> classByName;
	private static final Map<Class<?>, String> nameByClass;
	private static final Map<Class<?>, Integer> idByClass;
	
	static {
		Class<?> entityTypesClass = Version.getNMSClass("EntityTypes");
		classByName = (Map<String, Class<?>>)ReflectionHelper.getObjectInStaticField(entityTypesClass, "c");
		nameByClass = (Map<Class<?>, String>)ReflectionHelper.getObjectInStaticField(entityTypesClass, "d");
		idByClass = (Map<Class<?>, Integer>)ReflectionHelper.getObjectInStaticField(entityTypesClass, "f");
	}
	
	public static void registerEntity(String entityName, Class<?> entityClass) {
		Class<?> oldClass = classByName.get(entityName);
		classByName.put(entityName, entityClass);
		nameByClass.put(entityClass, entityName);
		idByClass.put(entityClass, idByClass.get(oldClass));
	}
	
	public static void removeAll(Collection<Entity> entities) {
		for(Entity entity : entities) {
			CustomEntity customEntity = WrapperFactory.castHandle(entity, CustomEntity.class);
			if(customEntity != null) customEntity.forceRemove();
			else entity.remove();
		}
	}
	
}
