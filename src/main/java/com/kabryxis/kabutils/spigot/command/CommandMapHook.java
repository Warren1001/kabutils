package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.command.CommandManager;
import com.kabryxis.kabutils.command.CommandManagerWork;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Method;

public class CommandMapHook implements CommandManagerWork {
	
	private CommandManager commandManager;
	private CommandMap commandMap;
	
	@Override
	public void initialize(CommandManager manager) {
		this.commandManager = manager;
		this.commandMap = hook();
	}
	
	private CommandMap hook() {
		try {
			Server server = Bukkit.getServer();
			Method getCommandMap = server.getClass().getDeclaredMethod("getCommandMap");
			getCommandMap.setAccessible(true);
			return (CommandMap)getCommandMap.invoke(server);
		} catch(Exception e) {
			throw new IllegalStateException("Could not retrieve CommandMap from Server.", e);
		}
	}
	
	@Override
	public void registerCommand(String alias) {
		commandMap.register(alias, new BukkitCommandWrapper(commandManager, alias));
	}
}
