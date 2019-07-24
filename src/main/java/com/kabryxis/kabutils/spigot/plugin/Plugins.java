package com.kabryxis.kabutils.spigot.plugin;

import org.bukkit.Bukkit;

public class Plugins {
	
	public static final String ProtocolLib = "ProtocolLib";
	
	public static boolean isInstalled(String name) {
		return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
	}
	
}
