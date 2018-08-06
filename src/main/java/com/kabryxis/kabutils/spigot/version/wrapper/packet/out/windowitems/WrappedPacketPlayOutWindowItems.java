package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems.impl.WrappedPacketPlayOutWindowItemsv1_8_R3;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Function;

public abstract class WrappedPacketPlayOutWindowItems implements WrappedPacket {
	
	private static final Function<Object[], WrappedPacketPlayOutWindowItems> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R3:
				supplier = WrappedPacketPlayOutWindowItemsv1_8_R3::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedPacketPlayOutWindowItems newInstance(int id, List<ItemStack> items) {
		return supplier.apply(new Object[]{ id, items });
	}
	
}
