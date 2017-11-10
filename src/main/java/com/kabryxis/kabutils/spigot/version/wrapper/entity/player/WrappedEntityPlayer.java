package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import org.bukkit.entity.Player;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;

public abstract class WrappedEntityPlayer<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedEntityPlayerv1_8_R1.class.getClass();
	}
	
	public abstract void set(Player player);
	
	public abstract void sendPacket(WrappedPacket<?> packet);
	
}
