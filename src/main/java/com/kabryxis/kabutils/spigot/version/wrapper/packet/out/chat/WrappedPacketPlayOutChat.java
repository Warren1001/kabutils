package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R3;

public interface WrappedPacketPlayOutChat extends WrappedPacket {
	
	Class<WrappedPacketPlayOutChatv1_8_R1> v1_8_R1 = WrappedPacketPlayOutChatv1_8_R1.class;
	Class<WrappedPacketPlayOutChatv1_8_R3> v1_8_R3 = WrappedPacketPlayOutChatv1_8_R3.class;
	
	WrappedPacketPlayOutChat EMPTY = newInstance("");
	
	static WrappedPacketPlayOutChat newInstance(String message) {
		return WrapperFactory.get(WrappedPacketPlayOutChat.class, new Class[] { Object.class }, new Object[] { message });
	}
	
	static WrappedPacketPlayOutChat newInstance() {
		return newInstance(null);
	}
	
}
