package com.kabryxis.kabutils.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComplexCommand extends SimpleCommand {
	
	private final boolean isGlobal;
	private final boolean isArgumentless;
	private final boolean hasNullArguments;
	
	public ComplexCommand(CommandManager commandManager, Object instance, Method method) {
		super(commandManager, instance, method);
		isGlobal = method.getParameterCount() == 0;
		isArgumentless = method.getParameterCount() == 1;
		String requiredArgs = com.args();
		hasNullArguments = requiredArgs.contains(",") || requiredArgs.contains("-");
	}
	
	@Override
	public boolean handle(CommandIssuer issuer, String[] args) {
		if(isGlobal) return invoke();
		Class<?>[] parameterTypes = method.getParameterTypes();
		if(isArgumentless) return invoke(commandManager.convertIssuer(parameterTypes[0], issuer));
		if((!hasNullArguments && args.length != parameterTypes.length - 1) || (hasNullArguments && !requiredArgs.contains(args.length))) return false;
		Object[] arguments = new Object[parameterTypes.length];
		arguments[0] = commandManager.convertIssuer(parameterTypes[0], issuer);
		if(hasNullArguments) arguments[1] = args.length;
		int offset = hasNullArguments ? 2 : 1;
		for(int i = 0; i < args.length; i++) {
			arguments[i + offset] = commandManager.convertArgument(parameterTypes[i + offset], args[i]);
		}
		for(int i = offset; i < arguments.length; i++) {
			Object obj = arguments[i];
			if(obj == null) {
				Class<?> parameterType = parameterTypes[i];
				if(parameterType.isPrimitive()) arguments[i] = parameterType == boolean.class ? false : 0;
			}
		}
		return invoke(arguments);
	}
	
	private boolean invoke(Object... arguments) {
		try {
			Object obj = method.invoke(instance, arguments);
			return alwaysReturnsTrue || (boolean)obj;
		} catch(IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
}
