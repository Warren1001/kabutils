package com.kabryxis.kabutils.command;

public interface CommandIssuer {
	
	boolean hasPermission(String permission);
	
	void sendMessage(String message);
	
}
