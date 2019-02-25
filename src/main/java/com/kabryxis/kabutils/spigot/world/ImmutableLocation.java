package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ImmutableLocation extends Location {
	
	public ImmutableLocation(Location loc) {
		super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public ImmutableLocation(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public ImmutableLocation(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}
	
	@Override
	public Location add(Location vec) {
		return new Location(getWorld(), getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ(), getYaw() + vec.getYaw(), getPitch() + vec.getPitch());
	}
	
	@Override
	public Location add(Vector vec) {
		return new Location(getWorld(), getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ(), getYaw(), getPitch());
	}
	
	@Override
	public Location add(double x, double y, double z) {
		return new Location(getWorld(), getX() + x, getY() + y, getZ() + z, getYaw(), getPitch());
	}
	
}
