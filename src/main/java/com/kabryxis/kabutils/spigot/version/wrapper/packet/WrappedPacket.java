package com.kabryxis.kabutils.spigot.version.wrapper.packet;

import org.bukkit.entity.Player;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;

public abstract class WrappedPacket<T> extends Wrappable<T> {
	
	public abstract void send(Player player);
	
}
