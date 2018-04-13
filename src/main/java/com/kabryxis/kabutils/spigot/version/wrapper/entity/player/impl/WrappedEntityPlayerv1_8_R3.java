package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WrappedEntityPlayerv1_8_R3 extends WrappedEntityPlayer<EntityPlayer> {
	
	@Override
	public void set(Player player) {
		set(((CraftPlayer)player).getHandle());
	}
	
	@Override
	public void sendPacket(WrappedPacket<?> packet) {
		get().playerConnection.sendPacket((Packet)packet.get());
	}
	
}
