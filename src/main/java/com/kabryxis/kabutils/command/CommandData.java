package com.kabryxis.kabutils.command;

public interface CommandData {
	
	Com getCom();
	
	String getName();
	
	boolean handle(CommandIssuer issuer, String[] args);
	
}
