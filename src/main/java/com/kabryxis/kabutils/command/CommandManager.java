package com.kabryxis.kabutils.command;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.data.Lists;
import com.kabryxis.kabutils.string.Strings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandManager {
	
	private final Map<String, List<CommandData>> methods = new HashMap<>();
	private final List<CommandManagerWork> extraWork = new ArrayList<>();
	
	private String commandPrefix = "-";
	
	public void addExtraWork(CommandManagerWork work) {
		extraWork.add(work);
		work.initialize(this);
	}
	
	public void registerCommands(Object obj) {
		for(Method method : obj.getClass().getDeclaredMethods()) {
			if(isCommand(method)) registerCommand(new CommandData(method.getAnnotation(Com.class), obj, method));
		}
	}
	
	protected boolean isCommand(Method method) {
		if(method.getAnnotation(Com.class) == null) return false;
		Class<?>[] params = method.getParameterTypes();
		if(params.length != 1 || params[0] != Command.class) {
			System.out.println(
					"Method '" + method.getName() + "' in class '" + method.getDeclaringClass().getSimpleName() + "' has a Com annotation but does not have a singular parameter of the " + Command.class.getName() +
							" class.");
			return false;
		}
		method.setAccessible(true);
		return true;
	}
	
	protected void registerCommand(CommandData data) {
		Function<? super String, ? extends List<CommandData>> action = Lists.getGenericCreator();
		for(String alias : data.getCom().aliases()) {
			methods.computeIfAbsent(alias.toLowerCase(), action).add(data);
			extraWork.forEach(work -> work.registerCommand(alias));
		}
	}
	
	public void handleCommand(CommandIssuer issuer, String message) {
		if(!isCommand(message)) return;
		boolean containsSpace = message.contains(" ");
		String alias = removeCommandPrefix(containsSpace ? message.substring(0, message.indexOf(' ')) : message);
		String[] args = containsSpace ? Strings.split(message.substring(message.indexOf(' ') + 1, message.length()), " ") : new String[0];
		Command command = Cache.get(Command.class);
		command.reuse(alias, args, issuer);
		methods.get(alias.toLowerCase()).forEach(d -> d.issue(command));
		command.cache();
	}
	
	public void handleCommand(CommandIssuer issuer, String alias, String[] args) {
		Command command = Cache.get(Command.class);
		command.reuse(alias, args, issuer);
		methods.get(alias).forEach(d -> d.issue(command));
		command.cache();
	}
	
	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
	}
	
	public String getCommandPrefix() {
		return commandPrefix;
	}
	
	public boolean isCommand(String message) {
		return message.startsWith(commandPrefix);
	}
	
	public String removeCommandPrefix(String message) {
		return message.substring(commandPrefix.length());
	}
	
}
