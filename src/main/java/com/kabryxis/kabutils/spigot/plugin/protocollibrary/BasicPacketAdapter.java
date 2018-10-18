package com.kabryxis.kabutils.spigot.plugin.protocollibrary;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class BasicPacketAdapter extends PacketAdapter {
	
	private final boolean receive;
	private final Consumer<PacketEvent> action;
	
	public BasicPacketAdapter(Plugin plugin, boolean receive, Consumer<PacketEvent> action, PacketType... packetTypes) {
		super(plugin, packetTypes);
		this.action = action;
		this.receive = receive;
	}
	
	/**
	 * This constructor calls the other constructor with a Consumer to cancel the events.
	 *
	 * @param plugin
	 * @param receive Whether to cancel events of packets being received (true) or sent (false)
	 * @param packetTypes
	 */
	public BasicPacketAdapter(Plugin plugin, boolean receive, PacketType... packetTypes) {
		this(plugin, receive, event -> event.setCancelled(true), packetTypes);
	}
	
	@Override
	public void onPacketReceiving(PacketEvent event) {
		if(receive) action.accept(event);
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
		if(!receive) action.accept(event);
	}
	
}
