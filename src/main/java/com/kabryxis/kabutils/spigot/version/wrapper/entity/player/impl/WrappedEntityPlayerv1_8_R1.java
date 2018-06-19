package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import com.kabryxis.kabutils.spigot.version.UnsupportedVersionException;
import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WrappedEntityPlayerv1_8_R1 extends WrappedEntityPlayer {
	
	private final EntityPlayer entityPlayer;
	
	public WrappedEntityPlayerv1_8_R1(Player player) {
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
		throw new UnsupportedOperationException(new UnsupportedVersionException(Version.v1_8_R3));
	}
	
}
