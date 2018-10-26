package com.kabryxis.kabutils.spigot.world;

import com.kabryxis.kabutils.data.Objects;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.StringJoiner;

public class Locations {
	
	public static String serialize(Location loc, boolean world, boolean floats) {
		Validate.notNull(loc, "loc cannot be null");
		if(world) Validate.notNull(loc.getWorld(), "Location's World cannot be null");
		StringJoiner joiner = new StringJoiner(",");
		if(world) joiner.add(loc.getWorld().getName());
		joiner.add(String.valueOf(loc.getX())).add(String.valueOf(loc.getY())).add(String.valueOf(loc.getZ()));
		if(floats) joiner.add(String.valueOf(loc.getYaw())).add(String.valueOf(loc.getPitch()));
		return joiner.toString();
	}
	
	public static Location deserialize(String string) {
		Validate.notNull(string, "string cannot be null");
		String[] args = string.split(",");
		if(args.length == 3) return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		if(args.length == 4) return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		if(args.length == 5) return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
				Float.parseFloat(args[3]), Float.parseFloat(args[4]));
		if(args.length == 7) return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
				Float.parseFloat(args[4]), Float.parseFloat(args[5]));
		return null;
	}
	
	public static Location deserialize(String string, WorldLoader worldLoader) {
		Validate.notNull(string, "string cannot be null");
		Validate.notNull(worldLoader, "worldLoader cannot be null");
		String[] args = string.split(",");
		if(args.length == 3) return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		if(args.length == 4) return new Location(worldLoader.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		if(args.length == 5) return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
				Float.parseFloat(args[3]), Float.parseFloat(args[4]));
		if(args.length == 7) return new Location(worldLoader.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
				Float.parseFloat(args[4]), Float.parseFloat(args[5]));
		return null;
	}
	
	public static Location deserialize(String string, World world) {
		Validate.notNull(string, "string cannot be null");
		Validate.notNull(world, "world cannot be null");
		String[] args = string.split(",");
		if(args.length == 3) return new Location(world, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		if(args.length == 4) {
			World provided = Bukkit.getWorld(args[0]);
			return new Location(Objects.getFirstNonnull(provided, world), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		}
		if(args.length == 5) return new Location(world, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]));
		if(args.length == 7) {
			World provided = Bukkit.getWorld(args[0]);
			return new Location(Objects.getFirstNonnull(provided, world), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
					Float.parseFloat(args[4]), Float.parseFloat(args[5]));
		}
		return null;
	}
	
}
