package com.kabryxis.kabutils.command;

import java.util.function.Function;

public interface CommandManager extends CommandDataManager {
	
	<T> void registerArgumentConverter(Class<T> clazz, Function<String, T> converter);
	
	<T> void registerIssuerConverter(Class<T> clazz, Function<CommandIssuer, T> converter);
	
	void registerSubclassesAndMethods(CommandDataManager dataManager, Object listener);
	
	<T> T convertIssuer(Class<T> clazz, CommandIssuer issuer);
	
	<T> T convertArgument(Class<T> clazz, String arg);
	
}
