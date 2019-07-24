package com.kabryxis.kabutils.spigot.plugin.protocollibrary;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.kabryxis.kabutils.spigot.game.player.GamePlayer;

import java.lang.reflect.InvocationTargetException;

public class ProtocolLibGamePlayerAdapter {
	
	private final GamePlayer player;
	private final ProtocolManager protocolManager;
	
	public ProtocolLibGamePlayerAdapter(GamePlayer player) {
		this.player = player;
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	public void sendPacket(PacketContainer container) {
		try {
			protocolManager.sendServerPacket(player.getPlayer(), container);
		} catch(InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
