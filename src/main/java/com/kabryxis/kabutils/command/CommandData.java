package com.kabryxis.kabutils.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandData {
	
	private final Com com;
	private final Object obj;
	private final Method method;
	private final Class<? extends CommandIssuer> clazz;
	
	public CommandData(Com com, Object obj, Method method) {
		this.com = com;
		this.obj = obj;
		this.method = method;
		this.clazz = (Class<? extends CommandIssuer>)method.getParameterTypes()[0];
	}
	
	public Com getCom() {
		return com;
	}
	
	public boolean issue(CommandIssuer issuer, String alias, String[] args) {
		if(!issuer.hasPermission(com.permission())) {
			issuer.sendMessage("no fuck u");
			return true;
		}
		Class<? extends CommandIssuer> objectClass = issuer.getClass();
		if(clazz == CommandIssuer.class || objectClass == clazz) {
			try {
				return (boolean)method.invoke(obj, issuer, alias, args);
			}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
