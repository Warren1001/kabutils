package com.kabryxis.kabutils.spigot.version;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;

public enum Version {
	
	UNKNOWN(-1, null, "unknown"),
	v1_8_R1(0, "v1_8_R1", "1.8"),
	v1_8_R2(1, "v1_8_R2", "1.8.3"),
	v1_8_R3(2, "v1_8_R3", "1.8.6"),
	v1_9_R1(3, "v1_9_R1", "1.9"),
	v1_9_R2(4, "v1_9_R2", "1.9.4"),
	v1_10_R1(5, "v1_10_R1", "1.10"),
	v1_11_R1(6, "v1_11_R1", "1.11"),
	v1_12_R1(7, "v1_12_R1", "1.12"),
	v1_13_R2(8, "v1_13_R2", "1.13.2"),
	v1_14_R1(9, "v1_14_R1", "1.14.4"),
	v1_15_R1(10, "v1_15_R1", "1.15");
	
	public static final Version VERSION = Version.getByName(Version.UNKNOWN.getImplementationNamespace());
	public static final String NMS_PACKAGE = "net.minecraft.server";
	public static final String OBC_PACKAGE = "org.bukkit.craftbukkit";
	
	public static Class<?> getNMSClass(String className) {
		return getClass(NMS_PACKAGE, className);
	}
	
	public static Class<?> getOBCClass(String className) {
		return getClass(OBC_PACKAGE, className);
	}
	
	private static Class<?> getClass(String packageString, String className) {
		try {
			return Class.forName(String.format("%s.%s.%s", packageString, Version.VERSION.getImplementationNamespace(), className));
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean isAtLeast1_9() {
		return Version.VERSION.isVersionAtLeast(Version.v1_9_R1);
	}
	
	public static Version getByName(String name) {
		Validate.notNull(name, "name cannot be null");
		Version version;
		try {
			version = Version.valueOf(name);
		} catch(IllegalArgumentException | NullPointerException ignore) {
			version = Version.UNKNOWN;
		}
		return version;
	}
	
	private final int id;
	private final String implementationNamespace;
	private final String bukkitVersion;
	
	Version(int id, String implementationNamespace, String bukkitVersion) {
		this.id = id;
		this.implementationNamespace = implementationNamespace == null ? getVersionString() : implementationNamespace;
		this.bukkitVersion = bukkitVersion;
	}
	
	private String getVersionString() {
		String version = Bukkit.getServer().getClass().getPackage().getName();
		return version.substring(version.lastIndexOf('.') + 1);
	}
	
	public String getImplementationNamespace() {
		return implementationNamespace;
	}
	
	public String getBukkitVersion() {
		return bukkitVersion;
	}
	
	public boolean isVersionAtLeast(Version version) {
		Validate.isTrue(this != Version.UNKNOWN && version != Version.UNKNOWN, "Cannot compare version to the generic unknown version");
		return id >= version.id;
	}
	
}
