package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.command.CommandIssuer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitCommandIssuer implements CommandIssuer {
	
	private final CommandSender sender;
	
	public BukkitCommandIssuer(CommandSender sender) {
		this.sender = sender;
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}
	
	@Override
	public void sendMessage(String message) {
		sender.sendMessage(message);
	}
	
	public boolean isPlayer() {
		return sender instanceof Player;
	}
	
	public Player getPlayer() {
		return (Player)sender;
	}
	
}
