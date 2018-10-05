package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems.impl.WrappedPacketPlayOutWindowItemsv1_8_R3;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface WrappedPacketPlayOutWindowItems extends WrappedPacket {
	
	Class<WrappedPacketPlayOutWindowItemsv1_8_R3> v1_8_R3 = WrappedPacketPlayOutWindowItemsv1_8_R3.class;
	
	static WrappedPacketPlayOutWindowItems newInstance(int id, List<ItemStack> items) {
		return WrapperFactory.getSupplier(WrappedPacketPlayOutWindowItems.class, Object[].class).apply(new Object[]{ id, items });
	}
	
}
