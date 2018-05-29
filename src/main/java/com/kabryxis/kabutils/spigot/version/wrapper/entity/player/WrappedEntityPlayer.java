package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import org.bukkit.entity.Player;

import java.util.function.Function;

public abstract class WrappedEntityPlayer implements Wrappable {
	
	private static final Function<Player, WrappedEntityPlayer> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedEntityPlayerv1_8_R1::new;
				break;
			case v1_8_R3:
				supplier = WrappedEntityPlayerv1_8_R3::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedEntityPlayer newInstance(Player player) {
		return supplier.apply(player);
	}
	
	public abstract void sendPacket(WrappedPacket packet);
	
}
