package com.kabryxis.kabutils.spigot.version.custom.slime.hitbox.impl;

import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.slime.hitbox.CustomHitbox;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomHitboxv1_8_R3 extends EntitySlime implements CustomHitbox {
	
	public static void register() {
		CustomEntityRegistry.registerEntity("Slime", CustomHitboxv1_8_R3.class);
	}
	
	public CustomHitboxv1_8_R3(Location loc, String ownerName) {
		this(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), ownerName);
	}
	
	public CustomHitboxv1_8_R3(World world, double x, double y, double z, String ownerName) {
		super(world);
		setSize(2);
		setLocation(x, y, z, 0F, 0F);
		setInvisible(true);
		setCustomNameVisible(false);
		setCustomName(ownerName);
		world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
	
	@Override // tick method
	public void t_() {}
	
	@Override
	public void collide(Entity entity) {}
	
	@Override
	public void forceRemove() {
		super.die();
	}
	
	@Override
	public CraftSlime getBukkitEntity() {
		return (CraftSlime)super.getBukkitEntity();
	}
	
}
