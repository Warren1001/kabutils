package com.kabryxis.kabutils.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandData {
	
	private final Com com;
	private final Object obj;
	private final Method method;
	
	public CommandData(Com com, Object obj, Method method) {
		this.com = com;
		this.obj = obj;
		this.method = method;
	}
	
	public Com getCom() {
		return com;
	}
	
	public void issue(Command command) {
		try {
			method.invoke(obj, command);
		}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
