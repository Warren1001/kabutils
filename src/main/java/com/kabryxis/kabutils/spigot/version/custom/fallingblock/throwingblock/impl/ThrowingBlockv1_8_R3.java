package com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.impl;

import com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.ThrowingBlock;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFallingSand;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ThrowingBlockv1_8_R3 extends EntityFallingBlock implements ThrowingBlock {
	
	private static long nextUID = 1L;
	
	private final long aliveTime;
	private final long spawnTimestamp;
	
	private long uid = nextUID++;
	
	public ThrowingBlockv1_8_R3(Location loc, Material type, byte data, long aliveTime) {
		this(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), Block.getById(type.getId()).fromLegacyData(data), aliveTime);
	}
	
	public ThrowingBlockv1_8_R3(World world, double x, double y, double z, IBlockData data, long aliveTime) {
		super(world, x, y, z, data);
		this.aliveTime = aliveTime;
		setLocation(x, y, z, 0F, 0F);
		world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
		spawnTimestamp = System.currentTimeMillis();
	}
	
	@Override
	public long getUniqueId() {
		return uid;
	}
	
	@Override
	public void forceRemove() {
		super.die();
	}
	
	@Override
	public void die() {
		if(canBePlaced()) forceRemove();
	}
	
	@Override
	public boolean canBePlaced() {
		return System.currentTimeMillis() - spawnTimestamp > aliveTime;
	}
	
	@Override
	public CraftFallingSand getBukkitEntity() {
		return (CraftFallingSand)super.getBukkitEntity();
	}
	
}
