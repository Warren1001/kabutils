package com.kabryxis.kabutils.spigot.version;

public class UnsupportedVersionException extends RuntimeException {
	
	private static final long serialVersionUID = -5998575359529989915L;
	
	public UnsupportedVersionException(Throwable throwable) {
		super("This version of Spigot is not supported. You are running " + Version.VERSION.getBukkitVersion() + ".", throwable);
	}
	
	public UnsupportedVersionException(Class<?> clazz) {
		super("Could not find an implementation for " + clazz.getSimpleName() + " for this Spigot version.");
	}
	
	public UnsupportedVersionException(Version minVersion) {
		super("This version of Spigot is not supported. You are running " + Version.VERSION.getBukkitVersion() + " but " + minVersion.getBukkitVersion() + " or higher is needed.");
	}
	
	public UnsupportedVersionException() {
		super("This version of Spigot is not supported. You are running " + Version.VERSION.getBukkitVersion() + ".");
	}
	
}
