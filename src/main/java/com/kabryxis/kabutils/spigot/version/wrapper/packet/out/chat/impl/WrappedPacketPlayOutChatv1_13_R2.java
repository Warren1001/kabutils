package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.WrappedPacketPlayOutChat;
import net.minecraft.server.v1_13_R2.ChatComponentText;
import net.minecraft.server.v1_13_R2.ChatMessageType;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WrappedPacketPlayOutChatv1_13_R2 implements WrappedPacketPlayOutChat {
	
	private PacketPlayOutChat packet;
	
	public WrappedPacketPlayOutChatv1_13_R2(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedPacketPlayOutChatv1_13_R2 setHandle(Object obj) {
		if(obj instanceof PacketPlayOutChat) packet = (PacketPlayOutChat)obj;
		else if(obj instanceof String) packet = new PacketPlayOutChat(new ChatComponentText((String)obj), ChatMessageType.GAME_INFO);
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
