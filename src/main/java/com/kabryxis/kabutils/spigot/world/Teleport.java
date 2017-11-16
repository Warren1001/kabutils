package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Location;

public class Teleport {
	
	public static Location[] getEquidistantPoints(Location center, int points, double radius) {
		Location[] locs = new Location[points];
		double slice = 2 * Math.PI / points, centerX = center.getX(), centerZ = center.getZ();
		for(int i = 0; i < points; i++) {
			double angle = slice * i;
			double x = centerX + radius * Math.cos(angle);
			double z = centerZ + radius * Math.sin(angle);
			double dx = centerX - x;
			double dz = centerZ - z;
			float yaw = -(dx != 0 ? (dx < 0 ? (float)(1.5 * Math.PI) : (float)(0.5 * Math.PI)) - (float)Math.atan(dz / dx) : (float)Math.PI) * 180F / (float)Math.PI;
			float pitch = ((float)(-Math.atan(0 / Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2))))) * 180F / (float)Math.PI;
			locs[i] = new Location(center.getWorld(), x - 0.5, center.getY() + 1.75, z - 0.5, yaw, pitch);
		}
		return locs;
	}
	
}
