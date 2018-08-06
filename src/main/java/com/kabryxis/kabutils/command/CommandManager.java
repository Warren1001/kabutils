package com.kabryxis.kabutils.command;

import com.kabryxis.kabutils.string.Strings;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class CommandManager {
	
	private final Map<String, CommandData> commandData = new HashMap<>();
	
	private String permissionMsg;
	private Predicate<Method> methodPredicate;
	private String prefix = "-";
	
	private Set<CommandWork> extraWork;
	
	public CommandManager(String permissionMsg, Predicate<Method> methodPredicate) {
		this.permissionMsg = permissionMsg;
		this.methodPredicate = methodPredicate;
	}
	
	public CommandManager(String permissionMsg) {
		this(permissionMsg, method -> {
			if(method.getAnnotation(Com.class) == null) return false;
			Class<?>[] parameterTypes = method.getParameterTypes();
			return parameterTypes.length == 3 && CommandIssuer.class.isAssignableFrom(parameterTypes[0]) && parameterTypes[1] == String.class &&
					parameterTypes[2] == String[].class;
		});
	}
	
	public void setPermissionMessage(String permissionMsg) {
		this.permissionMsg = permissionMsg;
	}
	
	public String getPermissionMessage() {
		return permissionMsg;
	}
	
	public void setMethodPredicate(Predicate<Method> methodPredicate) {
		this.methodPredicate = methodPredicate;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public boolean isCommand(String message) {
		return message.startsWith(prefix);
	}
	
	public String getAlias(String message) {
		return message.substring(prefix.length(), message.contains(" ") ? message.indexOf(" ") : message.length());
	}
	
	public String[] getArgs(String message) {
		return Strings.split(message.substring(message.indexOf(' ') + 1, message.length()), " ");
	}
	
	public void addExtraWork(CommandWork commandWork) {
		if(extraWork == null) extraWork = new HashSet<>();
		extraWork.add(commandWork);
	}
	
	public void registerListener(Object listener) {
		for(Method method : listener.getClass().getDeclaredMethods()) {
			if(methodPredicate.test(method)) registerMethod(method, listener);
		}
	}
	
	private void registerMethod(Method method, Object listener) {
		method.setAccessible(true);
		Com com = method.getAnnotation(Com.class);
		CommandData data = new CommandData(this, com, listener, method);
		for(String alias : com.aliases()) {
			commandData.put(alias, data);
		}
		if(extraWork != null) extraWork.forEach(work -> work.registerCommand(com));
	}
	
	public boolean handle(CommandIssuer issuer, String alias, String[] args) {
		CommandData data = commandData.get(alias.toLowerCase());
		Validate.notNull(data, "Tried to handle command '" + alias + "' but could not find any command data for this command.");
		return data.issue(issuer, alias, args);
	}
	
}
