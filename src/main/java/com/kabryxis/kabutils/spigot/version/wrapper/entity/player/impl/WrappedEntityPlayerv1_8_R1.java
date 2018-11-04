package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import com.kabryxis.kabutils.spigot.version.UnsupportedVersionException;
import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class WrappedEntityPlayerv1_8_R1 implements WrappedEntityPlayer {
	
	private static final Field containerCounter;
	
	static {
		Field localContainerCounter = null;
		try {
			localContainerCounter = EntityPlayer.class.getDeclaredField("containerCounter");
			localContainerCounter.setAccessible(true);
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		}
		containerCounter = localContainerCounter;
	}
	
	private EntityPlayer entityPlayer;
	
	public WrappedEntityPlayerv1_8_R1(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof EntityPlayer) entityPlayer = (EntityPlayer)obj;
		else if(obj instanceof Player) entityPlayer = ((CraftPlayer)obj).getHandle();
	}
	
	@Override
	public EntityPlayer getHandle() {
		return entityPlayer;
	}
	
	@Override
	public void sendPacket(WrappedPacket packet) {
		packet.send(entityPlayer.getBukkitEntity());
	}
	
	@Override
	public void teleportRelative(Location location) {
		throw new UnsupportedOperationException(new UnsupportedVersionException(Version.v1_8_R3));
	}
	
	@Override
	public int getContainerId() {
		return entityPlayer.activeContainer.windowId;
	}
	
	@Override
	public int getCurrentContainerCounter() {
		try {
			return containerCounter.getInt(entityPlayer) % 100;
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public double getLocX() {
		return entityPlayer.locX;
	}
	
	@Override
	public double getLocY() {
		return entityPlayer.locY;
	}
	
	@Override
	public double getLocZ() {
		return entityPlayer.locZ;
	}
	
	@Override
	public int getPing() {
		return entityPlayer.ping;
	}
	
	@Override
	public boolean isFireProof() {
		return entityPlayer.isFireProof();
	}
	
}
