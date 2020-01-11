package com.kabryxis.kabutils.spigot.version.custom.player;

import com.kabryxis.kabutils.spigot.version.custom.player.impl.WrappedInventoryv1_13_R2;
import com.kabryxis.kabutils.spigot.version.custom.player.impl.WrappedInventoryv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.entity.Player;

public interface WrappedInventory {
	
	Class<WrappedInventoryv1_8_R3> v1_8_R3 = WrappedInventoryv1_8_R3.class;
	Class<WrappedInventoryv1_13_R2> v1_13_R2 = WrappedInventoryv1_13_R2.class;
	
	static WrappedInventory wrap(Player player, ItemStackTracker tracker) {
		return WrapperFactory.getSupplier(WrappedInventory.class, Player.class, ItemStackTracker.class).apply(player, tracker);
	}
	
}
