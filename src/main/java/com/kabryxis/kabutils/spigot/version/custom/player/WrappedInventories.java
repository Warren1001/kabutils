package com.kabryxis.kabutils.spigot.version.custom.player;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.custom.player.impl.WrappedPlayerInventoryv1_8_R3;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class WrappedInventories {
	
	private static final BiConsumer<Player, ItemStackForwarder> playerInventoryWrapper;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R3:
				playerInventoryWrapper = WrappedPlayerInventoryv1_8_R3::new;
				break;
			default:
				playerInventoryWrapper = null;
				break;
		}
	}
	
	public static void wrapPlayerInventory(Player player, ItemStackForwarder forwarder) {
		playerInventoryWrapper.accept(player, forwarder);
	}
	
}
