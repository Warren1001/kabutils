package com.kabryxis.kabutils.spigot.serialization;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import com.kabryxis.kabutils.data.file.yaml.serialization.ConfigSectionSerializer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class WorldCreatorSerializer implements ConfigSectionSerializer {
	
	private static final BiMap<String, ChunkGenerator> chunkGenerators = HashBiMap.create();
	
	public static void registerChunkGenerator(String key, ChunkGenerator generator) {
		chunkGenerators.forcePut(key, generator);
	}
	
	protected Class<?>[] classes = { WorldCreator.class };
	
	@Override
	public String prefix() {
		return "wc";
	}
	
	@Override
	public Class<?>[] classes() {
		return classes;
	}
	
	@Override
	public ConfigSection serialize(Object obj) {
		ConfigSection section = new ConfigSection();
		WorldCreator creator = (WorldCreator)obj;
		section.put("world-name", creator.name());
		section.put("environment", creator.environment());
		section.put("type", creator.type());
		section.put("generator", chunkGenerators.inverse().get(creator.generator()));
		return section;
	}
	
	@Override
	public Object deserialize(ConfigSection section) {
		WorldCreator worldCreator = new WorldCreator(section.get("world-name"));
		worldCreator.environment(section.getEnum("environment", World.Environment.class));
		worldCreator.type(section.getEnum("type", WorldType.class));
		String generatorString = section.get("generator");
		if(generatorString != null) worldCreator.generator(chunkGenerators.get(generatorString));
		return worldCreator;
	}
	
}
