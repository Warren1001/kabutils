package com.kabryxis.kabutils.spigot.version.wrapper.world.world.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.util.LongHash;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.kabryxis.kabutils.spigot.concurrent.BukkitThreads;
import com.kabryxis.kabutils.spigot.version.wrapper.world.world.WrappedWorld;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Chunk;
import net.minecraft.server.v1_8_R1.ChunkProviderServer;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.World;

public class WrappedWorldv1_8_R1 extends WrappedWorld<World> {
	
	private static final IBlockData AIR = Block.getById(0).fromLegacyData(0);
	private static final BiConsumer<org.bukkit.Chunk, Integer> relight = (chunk, y) -> {
		BukkitThreads.syncTimer(new BukkitRunnable() { // TODO optimize
			
			boolean shouldErase = false, hasRan = false;
			
			@Override
			public void run() {
				if(shouldErase) {
					if(hasRan) {
						for(int x = 1; x < 16; x += 2) {
							for(int z = 1; z < 16; z += 2) {
								chunk.getBlock(x, y, z).setType(Material.AIR);
							}
						}
						cancel();
					}
					else {
						shouldErase = false;
						hasRan = true;
						for(int x = 0; x < 16; x += 2) {
							for(int z = 0; z < 16; z += 2) {
								chunk.getBlock(x, y, z).setType(Material.AIR);
							}
						}
					}
				}
				else {
					shouldErase = true;
					if(hasRan) {
						for(int x = 1; x < 16; x += 2) {
							for(int z = 1; z < 16; z += 2) {
								chunk.getBlock(x, y, z).setType(Material.OBSIDIAN);
							}
						}
					}
					else {
						for(int x = 0; x < 16; x += 2) {
							for(int z = 0; z < 16; z += 2) {
								chunk.getBlock(x, y, z).setType(Material.OBSIDIAN);
							}
						}
					}
				}
			}
			
		}, 1L, 5L);
	};
	
	private final World world;
	private final ChunkProviderServer chunkProvider;
	
	public WrappedWorldv1_8_R1(org.bukkit.World bukkitWorld) {
		this.world = ((CraftWorld)bukkitWorld).getHandle();
		this.chunkProvider = (ChunkProviderServer)world.N();
	}
	
	@Override
	public org.bukkit.World getWorld() {
		return world.getWorld();
	}
	
	@Override
	public String getName() {
		return getWorld().getName();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(int x, int y, int z, int type, byte data) {
		getWorld().getBlockAt(x, y, z).setTypeIdAndData(type, data, false);
	}
	
	@Override
	public void setBlockFast(int x, int y, int z, int type, byte data) {
		world.getChunkAt(x >> 4, z >> 4).a(new BlockPosition(x & 0x0f, y, z & 0x0f), type == 0 ? AIR : Block.getById(type).fromLegacyData(data));
	}
	
	@Override
	public void loadSchematic(Map<Long, List<int[]>> chunksData) {
		Set<org.bukkit.Chunk> chunks = new HashSet<>();
		int largestY = 0;
		for(Entry<Long, List<int[]>> entry : chunksData.entrySet()) {
			Chunk chunk = getChunk(entry.getKey());
			chunks.add(chunk.bukkitChunk);
			for(int[] data : entry.getValue()) {
				int y = data[1], type = data[3];
				if(y > largestY) largestY = y;
				chunk.a(new BlockPosition(data[0], y, data[2]), type == 0 ? AIR : Block.getById(type).fromLegacyData(data[4]));
			}
			refreshChunk(chunk.locX, chunk.locZ);
		}
		int y = largestY + 1;
		chunks.forEach(c -> relight.accept(c, y));
	}
	
	@Override
	public void eraseSchematic(Map<Long, List<int[]>> chunksData) {
		for(Entry<Long, List<int[]>> entry : chunksData.entrySet()) {
			Chunk chunk = getChunk(entry.getKey());
			for(int[] data : entry.getValue()) {
				chunk.a(new BlockPosition(data[0], data[1], data[2]), AIR);
			}
			refreshChunk(chunk.locX, chunk.locZ);
		}
	}
	
	private Chunk getChunk(long key) {
		int cx = getChunkX(key), cz = getChunkZ(key);
		Chunk chunk = world.getChunkAt(cx, cz);
		if(chunk.bukkitChunk == null) chunk = chunkProvider.originalGetChunkAt(cx, cz);
		return chunk;
	}
	
	@Override
	public org.bukkit.block.Block getBlock(int x, int y, int z) {
		return getWorld().getBlockAt(x, y, z);
	}
	
	@Override
	public int getBlockId(int x, int y, int z) {
		return Block.getId(world.getChunkAt(x >> 4, z >> 4).getTypeAbs(x & 0x0f, y, z & 0x0f));
	}
	
	@Override
	public void setMetadata(int x, int y, int z, String key, MetadataValue value) {
		getBlock(x, y, z).setMetadata(key, value);
	}
	
	@Override
	public void removeMetadata(int x, int y, int z, String key, Plugin plugin) {
		getBlock(x, y, z).removeMetadata(key, plugin);
	}
	
	@Override
	public boolean hasMetadata(int x, int y, int z, String key) {
		return getBlock(x, y, z).hasMetadata(key);
	}
	
	@Override
	public void eraseChunk(int cx, int cz) {
		Chunk chunk = world.getChunkAt(cx, cz);
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 256; y++) {
				for(int z = 0; z < 16; z++) {
					chunk.a(new BlockPosition(x, y, z), AIR);
				}
			}
		}
		refreshChunk(cx, cz);
	}
	
	@Override
	public void refreshChunk(int cx, int cz) {
		world.getWorld().refreshChunk(cx, cz);
	}
	
	@Override
	public long toLong(int cx, int cz) {
		return LongHash.toLong(cx, cz);
	}
	
	@Override
	public int getChunkX(long key) {
		return LongHash.msw(key);
	}
	
	@Override
	public int getChunkZ(long key) {
		return LongHash.lsw(key);
	}
	
}
