package com.kabryxis.kabutils.spigot.version;

public class UnsupportedVersionException extends RuntimeException {
	
	private static final long serialVersionUID = -5998575359529989915L;
	
	public UnsupportedVersionException(Throwable throwable) {
		super(String.format("This version of Minecraft is not supported. You are running Minecraft version %s", Version.VERSION.getBukkitVersion()), throwable);
	}
	
	public UnsupportedVersionException(Class<?> clazz) {
		super(String.format("Could not find an implementation of %s for Minecraft version %s", clazz.getSimpleName(), Version.VERSION.getBukkitVersion()));
	}
	
	public UnsupportedVersionException(Version minVersion) {
		super(String.format("This version of Minecraft is not supported. You are running Minecraft version %s but %s or higher is needed", Version.VERSION.getBukkitVersion(), minVersion.getBukkitVersion()));
	}
	
	public UnsupportedVersionException() {
		super(String.format("This version of Minecraft is not supported. You are running Minecraft version %s", Version.VERSION.getBukkitVersion()));
	}
	
}
