package com.kabryxis.kabutils.spigot.utility;

import org.bukkit.util.Vector;

public class Vectors {
	
	public static void extreme(Vector v1, Vector v2) {
		double x1 = v1.getX(), y1 = v1.getY(), z1 = v1.getZ();
		double x2 = v2.getX(), y2 = v2.getY(), z2 = v2.getZ();
		if(x1 > x2) {
			v2.setX(x1);
			v1.setX(x2);
		}
		if(y1 > y2) {
			v2.setY(y1);
			v1.setY(y2);
		}
		if(z1 > z2) {
			v2.setZ(z1);
			v1.setZ(z2);
		}
	}
	
}
