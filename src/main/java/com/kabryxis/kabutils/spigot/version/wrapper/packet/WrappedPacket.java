package com.kabryxis.kabutils.spigot.version.wrapper.packet;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import org.bukkit.entity.Player;

public interface WrappedPacket extends Wrappable {
	
	@Override
	WrappedPacket setHandle(Object obj);
	
	void send(Player player);
	
}
