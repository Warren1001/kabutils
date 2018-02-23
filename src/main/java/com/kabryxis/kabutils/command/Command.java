package com.kabryxis.kabutils.command;

import com.kabryxis.kabutils.cache.Cache;

public class Command {
	
	private String alias;
	private String[] args;
	private CommandIssuer issuer;
	
	public void reuse(String alias, String[] args, CommandIssuer issuer) {
		this.alias = alias;
		this.args = args;
		this.issuer = issuer;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public CommandIssuer getIssuer() {
		return issuer;
	}
	
	public void cache() {
		alias = null;
		args = null;
		issuer = null;
		Cache.cache(this);
	}
	
}
