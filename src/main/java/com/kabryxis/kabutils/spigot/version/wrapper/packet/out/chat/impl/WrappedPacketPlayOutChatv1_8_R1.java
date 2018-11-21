package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.WrappedPacketPlayOutChat;
import net.minecraft.server.v1_8_R1.ChatComponentText;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WrappedPacketPlayOutChatv1_8_R1 implements WrappedPacketPlayOutChat {
	
	private PacketPlayOutChat packet;
	
	public WrappedPacketPlayOutChatv1_8_R1(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedPacketPlayOutChatv1_8_R1 setHandle(Object obj) {
		if(obj instanceof PacketPlayOutChat) packet = (PacketPlayOutChat)obj;
		else if(obj instanceof String) packet = new PacketPlayOutChat(new ChatComponentText((String)obj), (byte)2);
		return this;
	}
	
	@Override
	public PacketPlayOutChat getHandle() {
		return packet;
	}
	
	@Override
	public void clear() {
		packet = null;
	}
	
	@Override
	public void send(Player player) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
}
