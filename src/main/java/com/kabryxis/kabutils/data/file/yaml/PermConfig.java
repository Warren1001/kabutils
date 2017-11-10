package com.kabryxis.kabutils.data.file.yaml;

public class PermConfig extends Config {
	
	private final int defaultPermission;
	
	public PermConfig(String name, int defaultPermission) {
		super(name);
		this.defaultPermission = defaultPermission;
	}
	
	public void setPermission(String id, int permission) {
		set(id + ".permission", permission);
	}
	
	public int getPermission(String id) {
		return get(id + ".permission", Integer.class, defaultPermission);
	}
	
	public boolean hasPermission(String id, int permission) {
		return getPermission(id) >= permission;
	}
	
}
