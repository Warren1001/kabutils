package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat;

import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R1;

public abstract class WrappedPacketPlayOutChat<T> extends WrappedPacket<T> {
	
	static { // include in maven shade plugin
		WrappedPacketPlayOutChatv1_8_R1.class.getClass();
	}
	
	public abstract void newInstance(String message);
	
}
