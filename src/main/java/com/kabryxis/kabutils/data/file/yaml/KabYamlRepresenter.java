package com.kabryxis.kabutils.data.file.yaml;

import com.kabryxis.kabutils.data.file.yaml.serialization.ConfigSectionSerializer;
import com.kabryxis.kabutils.data.file.yaml.serialization.MapSerializer;
import com.kabryxis.kabutils.data.file.yaml.serialization.SerializerException;
import com.kabryxis.kabutils.data.file.yaml.serialization.StringSerializer;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import java.util.HashMap;
import java.util.Map;

public class KabYamlRepresenter extends Representer {
	
	private final RepresentCustomString stringRepresenter = new RepresentCustomString();
	private final RepresentCustomMap mapRepresenter = new RepresentCustomMap();
	
	public KabYamlRepresenter() {
		multiRepresenters.put(ConfigSection.class, mapRepresenter);
	}
	
	public void registerSerializer(StringSerializer serializer) {
		Class<?>[] classes = serializer.classes();
		if(classes == null) return;
		for(Class<?> clazz : classes) {
			stringRepresenter.stringRepresenters.put(clazz, serializer);
			multiRepresenters.put(clazz, stringRepresenter);
		}
	}
	
	public void registerSerializer(MapSerializer serializer) {
		Class<?>[] classes = serializer.classes();
		if(classes == null) return;
		for(Class<?> clazz : classes) {
			mapRepresenter.mapRepresenters.put(clazz, serializer);
			multiRepresenters.put(clazz, mapRepresenter);
		}
	}
	
	public void registerSerializer(ConfigSectionSerializer serializer) {
		Class<?>[] classes = serializer.classes();
		if(classes == null) return;
		for(Class<?> clazz : classes) {
			mapRepresenter.configSectionRepresenters.put(clazz, serializer);
			multiRepresenters.put(clazz, mapRepresenter);
		}
	}
	
	protected class RepresentCustomString extends RepresentString {
		
		protected Map<Class<?>, StringSerializer> stringRepresenters = new HashMap<>();
		
		@Override
		public Node representData(Object obj) {
			StringSerializer representer = stringRepresenters.get(obj.getClass());
			if(representer == null) throw new SerializerException("Could not find a serializer for %s", obj.getClass().getName());
			String serialized;
			try {
				serialized = representer.serialize(obj);
				if(serialized == null) throw new SerializerException("The serializer %s returned a null serialization string while serializing %s", representer.getClass().getName(), obj);
				return super.representData(String.format("%s%s%s", representer.prefix(), Config.CUSTOM_INDICATOR, serialized));
			} catch(Exception e) {
				throw new SerializerException(e, "An error was thrown while the serializer %s was serializing %s", representer.getClass().getName(), obj);
			}
		}
		
	}
	
	protected class RepresentCustomMap extends RepresentMap {
		
		protected Map<Class<?>, MapSerializer> mapRepresenters = new HashMap<>();
		protected Map<Class<?>, ConfigSectionSerializer> configSectionRepresenters = new HashMap<>();
		
		@Override
		public Node representData(Object obj) {
			if(obj.getClass() == ConfigSection.class) return super.representData(obj);
			MapSerializer representer = mapRepresenters.get(obj.getClass());
			if(representer == null) {
				ConfigSectionSerializer configSectionRepresenter = configSectionRepresenters.get(obj.getClass());
				if(configSectionRepresenter == null) throw new SerializerException("Could not find a representer for %s", obj.getClass().getName());
				ConfigSection section;
				try {
					section = configSectionRepresenter.serialize(obj);
					if(section == null) throw new SerializerException("The representer %s returned a null serialization map while serializing %s",
							configSectionRepresenter.getClass().getName(), obj.getClass().getName());
					section.put(Config.CUSTOM_INDICATOR, configSectionRepresenter.prefix());
					return super.representData(section);
				} catch(Exception e) {
					throw new SerializerException(e, "An error was thrown while the representer %s was serializing %s", configSectionRepresenter.getClass().getName(), obj.getClass().getName());
				}
			}
			Map<String, Object> serialized;
			try {
				serialized = representer.serialize(obj);
				if(serialized == null) throw new SerializerException("The representer %s returned a null serialization map while serializing %s", representer.getClass().getName(), obj.getClass().getName());
				serialized.put(Config.CUSTOM_INDICATOR, representer.prefix());
				return super.representData(serialized);
			} catch(Exception e) {
				throw new SerializerException(e, "An error was thrown while the representer %s was serializing %s", representer.getClass().getName(), obj.getClass().getName());
			}
		}
		
	}
	
}
