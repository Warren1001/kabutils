package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.windowitems.WrappedPacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WrappedPacketPlayOutWindowItemsv1_8_R3 extends WrappedPacketPlayOutWindowItems {
	
	private final PacketPlayOutWindowItems packet;
	
	public WrappedPacketPlayOutWindowItemsv1_8_R3(Object[] objs) {
		int windowId = (Integer)objs[0];
		List<org.bukkit.inventory.ItemStack> bukkitItems = (List<org.bukkit.inventory.ItemStack>)objs[1];
		List<ItemStack> items = new ArrayList<>(bukkitItems.size());
		for(org.bukkit.inventory.ItemStack bukkitItem : bukkitItems) {
			items.add(CraftItemStack.asNMSCopy(bukkitItem));
		}
		packet = new PacketPlayOutWindowItems(windowId, items);
	}
	
	@Override
	public Object getObject() {
		return packet;
	}
	
	@Override
	public void send(Player player) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
}
