package com.kabryxis.kabutils.spigot.world.schematic;

import org.bukkit.Material;

public class SchematicEntry {
	
	private int x, y, z;
	private Material type;
	private int data; // TODO 1.13 update
	
	public SchematicEntry(int x, int y, int z, Material type, int data) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.data = data;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public Material getType() {
		return type;
	}
	
	public int getData() {
		return data;
	}
	
}
