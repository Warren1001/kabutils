package com.kabryxis.kabutils.spigot.world.schematic;

public interface SchematicCreator {
	
	void reset();
	
	SchematicCreator name(String name);
	
	SchematicCreator weight(int weight);
	
	void create();
	
}
