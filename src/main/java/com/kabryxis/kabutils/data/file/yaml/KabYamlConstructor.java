package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.Maps;
import com.kabryxis.kabutils.data.file.yaml.serialization.Serializer;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.HashMap;
import java.util.Map;

public class KabYamlConstructor extends SafeConstructor {
	
	private final ConstructCustomString stringConstructor = new ConstructCustomString();
	private final ConstructCustomConfigSection configSectionConstructor = new ConstructCustomConfigSection();
	
	public KabYamlConstructor() {
		yamlConstructors.put(Tag.STR, stringConstructor);
		yamlConstructors.put(Tag.MAP, configSectionConstructor);
	}
	
	public void registerSerializer(Serializer serializer) {
		switch(serializer.getType()) {
			case STRING:
				stringConstructor.stringConstructors.put(String.format("%s%s", serializer.getPrefix(), Config.CUSTOM_INDICATOR), serializer);
				break;
			case MAP:
				configSectionConstructor.mapConstructors.put(serializer.getPrefix(), serializer);
				break;
			case SECTION:
				configSectionConstructor.configSectionConstructors.put(serializer.getPrefix(), serializer);
				break;
		}
	}
	
	protected class ConstructCustomString extends ConstructYamlStr {
		
		protected Map<String, Serializer> stringConstructors = new HashMap<>();
		
		@Override
		public Object construct(Node node) {
			if(node.isTwoStepsConstruction()) throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
			String string = (String)super.construct(node);
			for(Map.Entry<String, Serializer> entry : stringConstructors.entrySet()) {
				if(string.startsWith(entry.getKey())) {
					Object obj;
					try {
						obj = entry.getValue().deserialize(string.split(Config.CUSTOM_INDICATOR, 2)[1]);
					} catch(Exception e) {
						e.printStackTrace();
						continue;
					}
					if(obj != null) return obj;
				}
			}
			return string;
		}
		
		@Override
		public void construct2ndStep(Node node, Object data) {
			throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
		}
		
	}
	
	protected class ConstructCustomMap extends ConstructYamlMap {
		
		protected Map<String, Serializer> mapConstructors = new HashMap<>();
		
		@Override
		public Object construct(Node node) {
			if(node.isTwoStepsConstruction()) throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
			Map<String, Object> map = Maps.convert((Map<?, ?>)super.construct(node), Object::toString, o -> o);
			Object key = map.get(Config.CUSTOM_INDICATOR);
			if(key instanceof String) {
				Serializer constructor = mapConstructors.get(key);
				if(constructor != null) {
					Object removed = map.remove(Config.CUSTOM_INDICATOR);
					Object obj = null;
					try {
						obj = constructor.deserialize(map);
					} catch(Exception e) {
						e.printStackTrace();
					}
					if(obj != null) return obj;
					else map.put(Config.CUSTOM_INDICATOR, removed);
				}
			}
			return new ConfigSection(map);
		}
		
		@Override
		public void construct2ndStep(Node node, Object object) {
			throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
		}
		
	}
	
	protected class ConstructCustomConfigSection extends ConstructCustomMap {
		
		protected Map<String, Serializer> configSectionConstructors = new HashMap<>();
		
		@Override
		public Object construct(Node node) {
			if(node.isTwoStepsConstruction()) throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
			Object obj = super.construct(node);
			if(obj instanceof ConfigSection) {
				ConfigSection section = (ConfigSection)obj;
				Object key = section.get(Config.CUSTOM_INDICATOR);
				if(key instanceof String) {
					Serializer constructor = configSectionConstructors.get(key);
					if(constructor != null) {
						Object removed = section.remove(Config.CUSTOM_INDICATOR);
						Object serialized = null;
						try {
							serialized = constructor.deserialize(section);
						} catch(Exception e) {
							e.printStackTrace();
						}
						if(serialized != null) return serialized;
						else section.put(Config.CUSTOM_INDICATOR, removed);
					}
				}
			}
			return obj;
		}
		
	}
	
	/*private class ConstructCustomList extends ConstructYamlSeq {
		
		private final Map<String, StringListSerializer> constructors = new HashMap<>();
		
		@Override
		public Object construct(Node node) {
			if(node.isTwoStepsConstruction()) throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
			List<?> list = (List<?>)super.construct(node);
			if(!list.isEmpty()) {
				Object objKey = list.get(0);
				if(objKey instanceof String) {
					String key = (String)objKey;
					list.remove(0);
					for(Map.Entry<String, StringListSerializer> entry : constructors.entrySet()) {
						if(key.equals(entry.getKey())) {
							Object obj;
							try {
								obj = entry.getValue().deserialize(Lists.convertList(list, String.class));
							} catch(Exception e) {
								e.printStackTrace();
								continue;
							}
							if(obj != null) return obj;
						}
					}
				}
			}
			return list;
		}
		
		@Override
		public void construct2ndStep(Node node, Object data) {
			throw new YAMLException(String.format("Unexpected referential mapping structure. Node: %s", node));
		}
		
	}*/
	
}
