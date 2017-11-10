package com.kabryxis.kabutils.spigot.version.wrapper.world.world;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.World;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.world.world.impl.WrappedWorldv1_8_R1;

public abstract class WrappedWorld<T> {
	
	private static final Function<World, WrappedWorld<?>> supplier;
	
	static { // include in maven shade plugin
		if(Version.VERSION == Version.v1_8_R1) {
			supplier = WrappedWorldv1_8_R1::new;
		}
		else supplier = null;
	}
	
	public static WrappedWorld<?> newInstance(World world) {
		return supplier.apply(world);
	}
	
	public abstract World getWorld();
	
	public abstract String getName();
	
	public abstract void setBlock(int x, int y, int z, int type, byte data);
	
	public abstract void setBlockFast(int x, int y, int z, int type, byte data);
	
	public abstract void loadSchematic(Map<Long, List<int[]>> chunksData);
	
	public abstract void eraseSchematic(Map<Long, List<int[]>> chunksData);
	
	public abstract org.bukkit.block.Block getBlock(int x, int y, int z);
	
	public abstract int getBlockId(int x, int y, int z);
	
	public abstract void setMetadata(int x, int y, int z, String key, MetadataValue value);
	
	public abstract void removeMetadata(int x, int y, int z, String key, Plugin plugin);
	
	public abstract boolean hasMetadata(int x, int y, int z, String key);
	
	public abstract void eraseChunk(int cx, int cz);
	
	public abstract void refreshChunk(int cx, int cz);
	
	public abstract long toLong(int cx, int cz);
	
	public abstract int getChunkX(long key);
	
	public abstract int getChunkZ(long key);
	
}
