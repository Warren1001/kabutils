package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Maps;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Map;

public class KabYamlConstructor extends SafeConstructor {
	
	public KabYamlConstructor() {
		yamlConstructors.put(Tag.MAP, new KabYamlConstructor.ConstructConfigSection());
	}
	
	private class ConstructConfigSection extends ConstructYamlMap {
		
		@Override
		public Object construct(Node node) {
			if(node.isTwoStepsConstruction()) throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
			return new ConfigSection(Maps.convertMap((Map<?, ?>)super.construct(node), Object::toString, o -> o));
		}
		
		@Override
		public void construct2ndStep(Node node, Object object) {
			throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
		}
		
	}
	
}
