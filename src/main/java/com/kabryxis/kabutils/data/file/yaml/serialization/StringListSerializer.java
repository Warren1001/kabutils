package com.kabryxis.kabutils.data.file.yaml.serialization;

import java.util.List;

public interface StringListSerializer extends Serializer<List<String>> {
	
	default boolean usesList() {
		return true;
	}
	
}
