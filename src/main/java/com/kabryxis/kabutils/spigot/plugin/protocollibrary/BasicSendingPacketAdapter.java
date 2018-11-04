package com.kabryxis.kabutils.spigot.plugin.protocollibrary;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class BasicSendingPacketAdapter extends PacketAdapter {
	
	private final Consumer<PacketEvent> action;
	
	public BasicSendingPacketAdapter(Plugin plugin, Consumer<PacketEvent> action, PacketType... packetTypes) {
		super(plugin, packetTypes);
		this.action = action;
	}
	
	public BasicSendingPacketAdapter(Plugin plugin, Consumer<PacketEvent> action, Iterable<PacketType> packetTypes) {
		super(plugin, packetTypes);
		this.action = action;
	}
	
	/**
	 * This constructor calls the other constructor with a Consumer to cancel the events.
	 */
	public BasicSendingPacketAdapter(Plugin plugin, PacketType... packetTypes) {
		this(plugin, event -> event.setCancelled(true), packetTypes);
	}
	
	/**
	 * This constructor calls the other constructor with a Consumer to cancel the events.
	 */
	public BasicSendingPacketAdapter(Plugin plugin, Iterable<PacketType> packetTypes) {
		this(plugin, event -> event.setCancelled(true), packetTypes);
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
		action.accept(event);
	}
	
}
