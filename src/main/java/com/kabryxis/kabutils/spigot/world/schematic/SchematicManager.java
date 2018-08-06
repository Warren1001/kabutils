package com.kabryxis.kabutils.spigot.world.schematic;

import com.kabryxis.kabutils.data.file.FileEndingFilter;
import com.kabryxis.kabutils.data.file.yaml.Config;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class SchematicManager {

	private final Map<String, Schematic> schematics = new HashMap<>();
	
	private final File folder;
	private final SchematicLoader loader;
	
	public SchematicManager(File folder, SchematicLoader loader) {
		this.folder = folder;
		if(!folder.exists()) folder.mkdirs();
		this.loader = loader;
	}
	
	public File getFolder() {
		return folder;
	}
	
	public void loadAll() {
		for(File schFile : Objects.requireNonNull(folder.listFiles(new FileEndingFilter(".sch")))) {
			File dataFile = new File(schFile.getParent(), schFile.getName().split("\\.")[0] + "-data.yml");
			loader.load(schFile, dataFile.exists() ? new Config(dataFile) : null);
		}
	}
	
	public void load(File schFile, @Nullable Config data) {
		loader.load(schFile, data);
	}
	
	public void create(String name, BlockSelection selection, @Nullable Config data) {
		loader.load(name, selection, data);
	}
	
	public void add(Schematic schematic) {
		schematics.put(schematic.getName(), schematic);
	}
	
	public Schematic get(String name) {
		return schematics.get(name);
	}
	
	public Set<Schematic> getSchematics() {
		return new HashSet<>(schematics.values());
	}

}
