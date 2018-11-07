package com.kabryxis.kabutils.spigot.version.custom.player;

import com.kabryxis.kabutils.spigot.version.custom.player.impl.WrappedInventoryv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.function.Consumer;

public interface WrappedInventory {
	
	Class<WrappedInventoryv1_8_R3> v1_8_R3 = WrappedInventoryv1_8_R3.class;
	
	static WrappedInventory wrap(Player player, Consumer<Collection<ItemStack>> forwarder) {
		return WrapperFactory.getSupplier(WrappedInventory.class, Player.class, Consumer.class).apply(player, forwarder);
	}
	
}
