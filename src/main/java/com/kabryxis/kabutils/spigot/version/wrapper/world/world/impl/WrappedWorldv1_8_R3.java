package com.kabryxis.kabutils.spigot.version.wrapper.world.world.impl;

import com.kabryxis.kabutils.spigot.concurrent.BukkitThreads;
import com.kabryxis.kabutils.spigot.version.wrapper.world.world.WrappedWorld;
import com.kabryxis.kabutils.spigot.world.ChunkEntry;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

public class WrappedWorldv1_8_R3 extends WrappedWorld<World> {
	
	private static final IBlockData AIR = Block.getById(0).fromLegacyData(0);
	private static final BiConsumer<org.bukkit.Chunk, Integer> relight = (chunk, y) -> BukkitThreads.syncTimer(new BukkitRunnable() { // TODO optimize
		
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
	
	private final World world;
	private final ChunkProviderServer chunkProvider;
	
	public WrappedWorldv1_8_R3(org.bukkit.World bukkitWorld) {
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
	public void setBlock(int x, int y, int z, Material type, byte data) {
		org.bukkit.block.Block block = getBlock(x, y, z);
		block.setType(type, false);
		block.setData(data);
	}
	
	@Override
	public void setBlockFast(int x, int y, int z, Material type, byte data) {
		world.getChunkAt(x >> 4, z >> 4).a(new BlockPosition(x & 0x0f, y, z & 0x0f), type == Material.AIR ? AIR : Block.getByName(type.name()).fromLegacyData(data));
	}
	
	@Override
	public void loadSchematic(Map<Long, List<ChunkEntry>> chunksData) {
		Set<org.bukkit.Chunk> chunks = new HashSet<>();
		int largestY = 0;
		for(Entry<Long, List<ChunkEntry>> entry : chunksData.entrySet()) {
			Chunk chunk = getChunk(entry.getKey());
			chunks.add(chunk.bukkitChunk);
			for(ChunkEntry data : entry.getValue()) {
				int x = data.getX(), y = data.getY(), z = data.getZ(), d = data.getData();
				Material type = data.getType();
				if(y > largestY) largestY = y;
				chunk.a(new BlockPosition(x, y, z), type == Material.AIR ? AIR : CraftMagicNumbers.getBlock(type).fromLegacyData(d));
			}
			refreshChunk(chunk.locX, chunk.locZ);
		}
		int y = largestY + 1;
		chunks.forEach(c -> relight.accept(c, y)); // TODO try changing to placing relight layer in the middle of the schematic if the height is > 3
		// TODO otherwise choose 1 + largestY. modify to replace existing block rather than setting back to air
	}
	
	@Override
	public void eraseSchematic(Map<Long, List<ChunkEntry>> chunksData) {
		for(Entry<Long, List<ChunkEntry>> entry : chunksData.entrySet()) {
			Chunk chunk = getChunk(entry.getKey());
			entry.getValue().forEach(data -> chunk.a(new BlockPosition(data.getX(), data.getY(), data.getZ()), AIR));
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
