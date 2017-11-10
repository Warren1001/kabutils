package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.WrappedPacketPlayOutChat;

import net.minecraft.server.v1_8_R1.ChatComponentText;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

public class WrappedPacketPlayOutChatv1_8_R1 extends WrappedPacketPlayOutChat<PacketPlayOutChat> {
	
	private String lastMessage;
	
	@Override
	public void newInstance(String message) {
		if(lastMessage == null || !lastMessage.equals(message)) {
			lastMessage = message;
			set(new PacketPlayOutChat(new ChatComponentText(message), (byte)2));
		}
	}
	
	@Override
	public void send(Player player) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(get());
	}
	
	@Override
	public void clear() {
		super.clear();
		lastMessage = null;
	}
	
}
