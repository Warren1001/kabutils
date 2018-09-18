package com.kabryxis.kabutils.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SimpleCommand implements CommandData {
	
	protected final CommandManager commandManager;
	protected final Object instance;
	protected final Method method;
	protected final Com com;
	private final String name;
	protected final boolean alwaysReturnsTrue;
	protected final Set<Integer> requiredArgs;
	
	public SimpleCommand(CommandManager commandManager, Object instance, Method method) {
		this.commandManager = commandManager;
		this.instance = instance;
		this.method = method;
		this.com = method.getAnnotation(Com.class);
		this.name = com.name().isEmpty() ? method.getName() : com.name();
		this.alwaysReturnsTrue = method.getReturnType() == void.class;
		this.requiredArgs = constructRequiredArgumentLengths(com.args());
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
	public boolean handle(CommandIssuer issuer, String[] args) {
		if(!requiredArgs.isEmpty() && !requiredArgs.contains(args.length)) return false;
		try {
			Object obj = method.invoke(instance, issuer, args);
			return alwaysReturnsTrue || (boolean)obj;
		}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Set<Integer> constructRequiredArgumentLengths(String requiredArgs) {
		Set<Integer> ints = new HashSet<>();
		if(requiredArgs.isEmpty()) return ints;
		if(requiredArgs.contains(",")) {
			String[] commaSplit = requiredArgs.split(",");
			for(String comma : commaSplit) {
				addRange(comma, ints);
			}
		}
		else addRange(requiredArgs, ints);
		return ints;
	}
	
	private void addRange(String arg, Set<Integer> ints) {
		if(arg.contains("-")) {
			String[] split = arg.split("-", 2);
			for(int i = Integer.parseInt(split[0]); i <= Integer.parseInt(split[1]); i++) {
				ints.add(i);
			}
		}
		else ints.add(Integer.parseInt(arg));
	}
	
}
