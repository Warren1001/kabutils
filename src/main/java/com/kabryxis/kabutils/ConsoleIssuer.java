package com.kabryxis.kabutils;

import com.kabryxis.kabutils.command.CommandIssuer;

public class ConsoleIssuer implements CommandIssuer {
	
	private static ConsoleIssuer INSTANCE;
	
	public static ConsoleIssuer get() {
		if(INSTANCE == null) INSTANCE = new ConsoleIssuer();
		return INSTANCE;
	}
	
	private ConsoleIssuer() {}
	
	@Override
	public void sendMessage(String message) {
		System.out.println(message);
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return true;
	}
	
}
