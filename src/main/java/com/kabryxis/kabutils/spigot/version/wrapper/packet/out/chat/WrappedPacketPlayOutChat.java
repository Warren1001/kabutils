package com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.out.chat.impl.WrappedPacketPlayOutChatv1_8_R3;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class WrappedPacketPlayOutChat implements WrappedPacket {
	
	private static final Function<String, WrappedPacketPlayOutChat> stringSupplier;
	private static final Supplier<WrappedPacketPlayOutChat> supplier;
	public static final WrappedPacketPlayOutChat EMPTY;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				stringSupplier = WrappedPacketPlayOutChatv1_8_R1::new;
				supplier = WrappedPacketPlayOutChatv1_8_R1::new;
				break;
			case v1_8_R3:
				stringSupplier = WrappedPacketPlayOutChatv1_8_R3::new;
				supplier = WrappedPacketPlayOutChatv1_8_R3::new;
				break;
			default:
				stringSupplier = null;
				supplier = null;
				break;
		}
		EMPTY = newInstance("");
	}
	
	public static WrappedPacketPlayOutChat newInstance(String message) {
		return stringSupplier.apply(message);
	}
	
	public static WrappedPacketPlayOutChat newInstance() {
		return supplier.get();
	}
	
	public abstract void setMessage(String message);
	
}
