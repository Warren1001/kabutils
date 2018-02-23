package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Material;

import com.kabryxis.kabutils.cache.Cache;

public class ChunkEntry {
	
	private int x, y, z;
	private Material type;
	private int data; // TODO 1.13 update
	
	public void reuse(int x, int y, int z, Material type, int data) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.data = data;
	}
	
	public void reuse(int x, int y, int z, Material type) {
		reuse(x, y, z, type, 0);
	}
	
	public void reuse(int x, int y, int z) {
		reuse(x, y, z, Material.AIR);
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
	
	public void cache() {
		x = 0;
		y = 0;
		z = 0;
		type = null;
		data = 0;
		Cache.cache(this);
	}
	
}
