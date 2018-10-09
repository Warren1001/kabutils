package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.command.CommandData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class BukkitCommandWrapper extends Command {
	
	private final CommandData commandData;
	
	public BukkitCommandWrapper(CommandData commandData) {
		super(commandData.getName(), commandData.getCom().description(), commandData.getCom().usage(), Arrays.asList(commandData.getCom().aliases()));
		this.commandData = commandData;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		try {
			return commandData.handle(new BukkitCommandIssuer(sender), args);
		}
		catch(Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
}
