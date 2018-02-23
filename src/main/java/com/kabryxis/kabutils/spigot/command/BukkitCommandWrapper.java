package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.command.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitCommandWrapper extends Command {
	
	private final CommandManager manager;
	
	public BukkitCommandWrapper(CommandManager manager, String name) {
		super(name);
		this.manager = manager;
	}
	
	public boolean execute(CommandSender sender, String alias, String[] args) {
		BukkitCommandIssuer commandIssuer = Cache.get(BukkitCommandIssuer.class);
		commandIssuer.reuse(sender);
		manager.handleCommand(commandIssuer, alias.toLowerCase(), args);
		return true;
	}
	
}
