package com.kabryxis.kabutils.spigot.serialization;

import com.kabryxis.kabutils.data.file.yaml.serialization.SerializationType;
import com.kabryxis.kabutils.data.file.yaml.serialization.Serializer;
import com.kabryxis.kabutils.spigot.world.Locations;
import org.bukkit.Location;

public class LocationSerializer implements Serializer {
	
	protected Class<?>[] classes = { Location.class };
	
	@Override
	public String getPrefix() {
		return "l";
	}
	
	@Override
	public SerializationType getType() {
		return SerializationType.STRING;
	}
	
	@Override
	public Class<?>[] getClasses() {
		return classes;
	}
	
	@Override
	public String serialize(Object obj) {
		return Locations.serialize((Location)obj, true, true);
	}
	
	@Override
	public Location deserialize(Object obj) {
		return Locations.deserialize((String)obj);
	}
	
}
