package com.kabryxis.kabutils.spigot.world.schematic;

import com.kabryxis.kabutils.data.file.yaml.Config;

import javax.annotation.Nullable;
import java.io.File;

public interface SchematicLoader {
	
	void load(File file, @Nullable Config data);
	
	void load(String name, BlockSelection selection, @Nullable Config data);
	
}
