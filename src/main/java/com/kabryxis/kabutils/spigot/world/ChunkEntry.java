package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Material;

public class ChunkEntry {
	
	private final int x, y, z;
	private final Material type;
	private final int data; // TODO 1.13 update
	
	public ChunkEntry(int x, int y, int z, Material type, int data) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.data = data;
	}
	
	public ChunkEntry(int x, int y, int z, Material type) {
		this(x, y, z, type, 0);
	}
	
	public ChunkEntry(int x, int y, int z) {
		this(x, y, z, Material.AIR);
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
