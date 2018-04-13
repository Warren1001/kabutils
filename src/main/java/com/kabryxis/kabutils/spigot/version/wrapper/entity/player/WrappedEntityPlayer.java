package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrapper;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import org.bukkit.entity.Player;

public abstract class WrappedEntityPlayer<T> extends Wrapper<T> {
	
	static { // include in maven shade plugin
		WrappedEntityPlayerv1_8_R1.class.getClass();
		WrappedEntityPlayerv1_8_R3.class.getClass();
	}
	
	public abstract void set(Player player);
	
	public abstract void sendPacket(WrappedPacket<?> packet);
	
}
