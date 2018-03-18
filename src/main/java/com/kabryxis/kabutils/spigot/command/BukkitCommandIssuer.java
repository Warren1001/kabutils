package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.cache.Cache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kabryxis.kabutils.command.CommandIssuer;

public class BukkitCommandIssuer implements CommandIssuer {
	
	private CommandSender sender;
	
	public void reuse(CommandSender sender) {
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
	
	@Override
	public void cache() {
		sender = null;
		Cache.cache(this);
	}
	
	public boolean isPlayer() {
		return sender instanceof Player;
	}
	
	public Player getPlayer() {
		return (Player)sender;
	}
	
}
