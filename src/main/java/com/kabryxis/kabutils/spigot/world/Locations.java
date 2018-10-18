package com.kabryxis.kabutils.spigot.world;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;

public class Locations {
	
	public static String serialize(Location loc, boolean floats) {
		Validate.notNull(loc, "loc cannot be null.");
		String string = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
		if(floats) string += "," + loc.getYaw() + "," + loc.getPitch();
		return string;
	}
	
	public static Location deserialize(String string, @Nullable WorldLoader worldLoader) {
		Validate.notNull(string, "string cannot be null.");
		String[] args = string.split(",");
		Validate.isTrue(args.length == 4 || args.length == 6, "string needs to have 4 or 6 arguments for a Location.");
		World world = Bukkit.getWorld(args[0]);
		if(world == null) {
			if(worldLoader == null) throw new IllegalArgumentException("'" + args[0] + "' world was not found. Perhaps it's not loaded?");
			world = worldLoader.getWorld(args[0]);
		}
		double x = Double.parseDouble(args[1]);
		double y = Double.parseDouble(args[2]);
		double z = Double.parseDouble(args[3]);
		return args.length == 6 ? new Location(world, x, y, z, Float.parseFloat(args[4]), Float.parseFloat(args[5])) : new Location(world, x, y, z);
	}
	
	public static Location deserialize(String string) {
		return deserialize(string, null);
	}
	
	public static Location deserialize(World world, String string) {
		Validate.notNull(world, "world cannot be null.");
		Validate.notNull(string, "string cannot be null.");
		String[] args = string.split(",");
		Validate.isTrue(args.length == 3 || args.length == 5, "string needs to have 3 or 5 arguments for a location when providing a world.");
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		double z = Double.parseDouble(args[2]);
		return args.length == 5 ? new Location(world, x, y, z, Float.parseFloat(args[3]), Float.parseFloat(args[4])) : new Location(world, x, y, z);
	}
	
}
