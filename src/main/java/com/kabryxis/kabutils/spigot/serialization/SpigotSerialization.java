package com.kabryxis.kabutils.spigot.serialization;

import com.kabryxis.kabutils.data.file.yaml.Config;
import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import com.kabryxis.kabutils.spigot.world.Locations;
import org.bukkit.Location;

public class SpigotSerialization {
	
	public static void registerSerializers() {
		Config.registerSerializer(new LocationSerializer());
		Config.registerSerializer(new WorldCreatorSerializer());
		Config.registerSerializer(new ItemBuilderSerializer());
		ConfigSection.addDeserializer(Location.class, Locations::deserialize);
	}
	
}
