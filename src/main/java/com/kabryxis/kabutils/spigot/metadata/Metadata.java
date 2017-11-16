package com.kabryxis.kabutils.spigot.metadata;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.kabryxis.kabutils.spigot.plugin.SpoofPlugin;

public class Metadata {
	
	private static final Plugin plugin = SpoofPlugin.get();
	private static final Object dummy = new Object();
	private static final MetadataValue emptyValue = new FixedMetadataValue(plugin, dummy);
	
	public static MetadataValue getEmptyMetadataValue() {
		return emptyValue;
	}
	
}
