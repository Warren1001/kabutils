package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Set;

public class WrappedEntityPlayerv1_8_R3 extends WrappedEntityPlayer {
	
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
	
	private static final Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> teleportFlags = EnumSet.of(PacketPlayOutPosition.EnumPlayerTeleportFlags.X, PacketPlayOutPosition.EnumPlayerTeleportFlags.Y,
			PacketPlayOutPosition.EnumPlayerTeleportFlags.Z);
	
	private EntityPlayer entityPlayer;
	
	public WrappedEntityPlayerv1_8_R3(Player player) {
		setPlayer(player);
	}
	
	public WrappedEntityPlayerv1_8_R3() {}
	
	@Override
	public Object getObject() {
		return entityPlayer;
	}
	
	@Override
	public void setPlayer(Player player) {
		this.entityPlayer = ((CraftPlayer)player).getHandle();
	}
	
	@Override
	public void sendPacket(WrappedPacket packet) {
		packet.send(entityPlayer.getBukkitEntity());
	}
	
	@Override
	public void teleportRelative(Location location) {
		entityPlayer.playerConnection.sendPacket(new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), 0F, 0F, teleportFlags));
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
	
}
