package com.kabryxis.kabutils.spigot.serialization;

import com.kabryxis.kabutils.data.file.yaml.serialization.StringSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer implements StringSerializer {
	
	protected Class<?>[] classes = { Location.class };
	
	@Override
	public String prefix() {
		return "l";
	}
	
	@Override
	public Class<?>[] classes() {
		return classes;
	}
	
	@Override
	public String serialize(Object obj) {
		Location loc = (Location)obj;
		return String.format("%s,%s,%s,%s,%s,%s", loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	@Override
	public Object deserialize(String string) {
		String[] args = string.split(",");
		return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
	}
	
}
