package com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.WrappedEntityPlayer;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.Packet;

public class WrappedEntityPlayerv1_8_R1 extends WrappedEntityPlayer<EntityPlayer> {
	
	@Override
	public void set(Player player) {
		set(((CraftPlayer)player).getHandle());
	}
	
	@Override
	public void sendPacket(WrappedPacket<?> packet) {
		get().playerConnection.sendPacket((Packet)packet.get());
	}
	
}
