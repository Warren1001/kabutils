package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Set;

public class WrappedEntityPlayerv1_8_R3 extends WrappedEntityPlayer {
	
	private static final Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> teleportFlags = EnumSet.of(PacketPlayOutPosition.EnumPlayerTeleportFlags.X, PacketPlayOutPosition.EnumPlayerTeleportFlags.Y,
			PacketPlayOutPosition.EnumPlayerTeleportFlags.Z);
	
	private final EntityPlayer entityPlayer;
	
	public WrappedEntityPlayerv1_8_R3(Player player) {
		this.entityPlayer = ((CraftPlayer)player).getHandle();
	}
	
	@Override
	public Object getObject() {
		return entityPlayer;
	}
	
	@Override
	public void sendPacket(WrappedPacket packet) {
		packet.send(entityPlayer.getBukkitEntity());
	}
	
	@Override
	public void teleportRelative(Location location) {
		entityPlayer.playerConnection.sendPacket(new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), 0F, 0F, teleportFlags));
	}
	
}
