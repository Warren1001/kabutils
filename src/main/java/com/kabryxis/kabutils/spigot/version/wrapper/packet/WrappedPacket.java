package com.kabryxis.kabutils.spigot.version.wrapper.packet;

import org.bukkit.entity.Player;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrapper;

public abstract class WrappedPacket<T> extends Wrapper<T> {
	
	public abstract void send(Player player);
	
}
