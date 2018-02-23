package com.kabryxis.kabutils.command;

public interface CommandManagerWork {
	
	void initialize(CommandManager manager);
	
	void registerCommand(String alias);
	
}
