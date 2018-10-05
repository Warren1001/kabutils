package com.kabryxis.kabutils.data.file.yaml;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

public class KabYamlRepresenter extends Representer {
	
	public KabYamlRepresenter() {
		multiRepresenters.put(ConfigSection.class, new KabYamlRepresenter.RepresentConfigSection());
	}
	
	private class RepresentConfigSection extends RepresentMap {
		
		@Override
		public Node representData(Object obj) {
			return super.representData(((ConfigSection)obj).getValues(false));
		}
		
	}
	
}
