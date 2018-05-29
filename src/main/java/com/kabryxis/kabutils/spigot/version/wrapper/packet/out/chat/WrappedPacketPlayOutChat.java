package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R3;

import java.util.function.Function;

public abstract class WrappedPacketPlayOutChat implements WrappedPacket {
	
	private static final Function<String, WrappedPacketPlayOutChat> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedPacketPlayOutChatv1_8_R1::new;
				break;
			case v1_8_R3:
				supplier = WrappedPacketPlayOutChatv1_8_R3::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedPacketPlayOutChat newInstance(String message) {
		return supplier.apply(message);
	}
	
}
