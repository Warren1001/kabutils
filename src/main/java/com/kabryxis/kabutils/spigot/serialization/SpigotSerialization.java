package com.kabryxis.kabutils.spigot.serialization;

import com.kabryxis.kabutils.data.file.yaml.Config;

public class SpigotSerialization {
	
	public static void registerSerializers() {
		Config.registerSerializer(new LocationSerializer());
		Config.registerSerializer(new WorldCreatorSerializer());
	}
	
}
