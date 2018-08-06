package com.kabryxis.kabutils.spigot.world.schematic;

import java.util.Set;

public interface Schematic {
	
	String getName();
	
	Set<SchematicEntry> getSchematicEntries();
	
}
