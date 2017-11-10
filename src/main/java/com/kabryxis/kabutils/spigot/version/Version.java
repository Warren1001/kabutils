package com.kabryxis.kabutils.spigot.version;

import org.bukkit.Bukkit;

public class Version {
	
	public final static Version UNSUPPORTED = new Version(-1, null, null);
	
	public final static Version v1_8_R1 = new Version(0, "v1_8_R1", "1.8");
	public final static Version v1_8_R2 = new Version(1, "v1_8_R2", "1.8.3");
	public final static Version v1_8_R3 = new Version(2, "v1_8_R3", "1.8.6");
	public final static Version v1_9_R1 = new Version(3, "v1_9_R1", "1.9");
	public final static Version v1_9_R2 = new Version(4, "v1_9_R2", "1.9.4");
	public final static Version v1_10_R1 = new Version(5, "v1_10_R1", "1.10");
	public final static Version v1_11_R1 = new Version(6, "v1_11_R1", "1.11");
	public final static Version v1_12_R1 = new Version(7, "v1_10_R1", "1.12");
	
	public final static Version VERSION;
	
	static {
		String version = Bukkit.getServer().getClass().getPackage().getName();
		version = version.substring(version.lastIndexOf('.') + 1);
		switch(version) {
		case "v1_8_R1":
			VERSION = v1_8_R1;
			break;
		case "v1_8_R2":
			VERSION = v1_8_R2;
			break;
		case "v1_8_R3":
			VERSION = v1_8_R3;
			break;
		case "v1_9_R1":
			VERSION = v1_9_R1;
			break;
		case "v1_9_R2":
			VERSION = v1_9_R2;
			break;
		case "v1_10_R1":
			VERSION = v1_10_R1;
			break;
		case "v1_11_R1":
			VERSION = v1_11_R1;
			break;
		case "v1_12_R1":
			VERSION = v1_12_R1;
			break;
		default:
			VERSION = UNSUPPORTED;
			break;
		}
	}
	
	private final int id;
	private final String implementationNamespace;
	private final String bukkitVersion;
	
	private Version(int id, String implementationNamespace, String bukkitVersion) {
		this.id = id;
		this.implementationNamespace = implementationNamespace;
		this.bukkitVersion = bukkitVersion;
	}
	
	public int toInt() {
		if(id == -1) throw new UnsupportedVersionException();
		return id;
	}
	
	public String getImplementationNamespace() {
		if(implementationNamespace == null) throw new UnsupportedVersionException();
		return implementationNamespace;
	}
	
	public String getBukkitVersion() {
		if(bukkitVersion == null) throw new UnsupportedVersionException();
		return bukkitVersion;
	}
	
	public boolean isVersionAtLeast(Version version) {
		return id >= version.toInt();
	}
	
}
