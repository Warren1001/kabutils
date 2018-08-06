package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.WrappedPacketPlayOutChat;
import net.minecraft.server.v1_8_R1.ChatComponentText;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WrappedPacketPlayOutChatv1_8_R1 extends WrappedPacketPlayOutChat {
	
	private PacketPlayOutChat packet;
	
	public WrappedPacketPlayOutChatv1_8_R1(String message) {
		setMessage(message);
	}
	
	public WrappedPacketPlayOutChatv1_8_R1() {}
	
	@Override
	public Object getObject() {
		return packet;
	}
	
	@Override
	public void send(Player player) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
	@Override
	public void setMessage(String message) {
		this.packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
	}
	
}
