package com.kabryxis.kabutils.spigot.entity;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.utility.ReflectionHelper;

import java.lang.reflect.Field;

public class Entities {
	
	private static Field entityIdField = ReflectionHelper.getField(Version.getNMSClass("Entity"), "entityCount");
	
	public static int nextEntityId() {
		int entityId = ReflectionHelper.getIntInStaticField(entityIdField) + 1;
		ReflectionHelper.setIntInStaticField(entityIdField, entityId);
		return entityId;
	}
	
}
