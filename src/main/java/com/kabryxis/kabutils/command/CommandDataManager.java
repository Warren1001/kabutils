package com.kabryxis.kabutils.command;

public interface CommandDataManager {
	
	void registerCommandData(CommandData commandData);
	
	CommandDataManager registerListener(Object listener);
	
	void registerListeners(Object... listeners);
	
}
