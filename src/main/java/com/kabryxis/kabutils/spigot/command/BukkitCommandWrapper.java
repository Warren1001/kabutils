package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.command.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitCommandWrapper extends Command {
	
	private final CommandManager manager;
	
	public BukkitCommandWrapper(CommandManager manager, String name) {
		super(name);
		this.manager = manager;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		return manager.handle(new BukkitCommandIssuer(sender), alias.toLowerCase(), args);
	}
	
}
