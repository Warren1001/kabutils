package com.kabryxis.kabutils.command;

public interface CommandIssuer {
	
	void sendMessage(String message);
	
	boolean hasPermission(String permission);
	
	void cache();
	
}
