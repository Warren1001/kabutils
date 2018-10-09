package com.kabryxis.kabutils.command;

import com.kabryxis.kabutils.data.Arrays;

import java.util.HashMap;
import java.util.Map;

public class SubcommandHandler implements CommandData, CommandDataManager {
	
	private final Map<String, CommandData> commandDataMap = new HashMap<>();
	
	private final CommandManager commandManager;
	private final Class<?> commandClass;
	private final Com com;
	private final String name;
	
	public SubcommandHandler(CommandManager commandManager, Object instance) {
		this.commandManager = commandManager;
		this.commandClass = instance.getClass();
		this.com = commandClass.getAnnotation(Com.class);
		this.name = com.name().isEmpty() ? commandClass.getName() : com.name();
		commandManager.registerSubclassesAndMethods(this, instance);
	}
	
	@Override
	public Com getCom() {
		return com;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void registerCommandData(CommandData commandData) {
		commandDataMap.put(commandData.getName().toLowerCase(), commandData);
		for(String alias : commandData.getCom().aliases()) {
			commandDataMap.put(alias.toLowerCase(), commandData);
		}
	}
	
	@Override
	public SubcommandHandler registerListener(Object listener) {
		if(listener.getClass().getAnnotation(Com.class) != null) {
			SubcommandHandler subcommandHandler = new SubcommandHandler(commandManager, listener);
			registerCommandData(subcommandHandler);
			return subcommandHandler;
		}
		else {
			commandManager.registerSubclassesAndMethods(this, listener);
			return this;
		}
	}
	
	@Override
	public void registerListeners(Object... listeners) {
		for(Object listener : listeners) {
			registerListener(listener);
		}
	}
	
	@Override
	public boolean handle(CommandIssuer issuer, String[] args) {
		return args.length > 0 && commandDataMap.get(args[0].toLowerCase()).handle(issuer, Arrays.splitArray(args, 1));
	}
	
}
