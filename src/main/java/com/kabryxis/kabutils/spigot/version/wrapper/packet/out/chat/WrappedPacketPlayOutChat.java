package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R3;

public interface WrappedPacketPlayOutChat extends WrappedPacket {

	Class<WrappedPacketPlayOutChatv1_8_R3> v1_8_R3 = WrappedPacketPlayOutChatv1_8_R3.class;
	
	WrappedPacketPlayOutChat EMPTY = newInstance("");
	
	static WrappedPacketPlayOutChat newInstance(String message) {
		return WrapperFactory.getSupplier(WrappedPacketPlayOutChat.class, Object.class).apply(message);
	}
	
	static WrappedPacketPlayOutChat newInstance() {
		return newInstance(null);
	}
	
	@Override
	WrappedPacketPlayOutChat setHandle(Object obj);
	
}
