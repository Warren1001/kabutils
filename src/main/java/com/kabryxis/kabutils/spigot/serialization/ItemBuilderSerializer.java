package com.kabryxis.kabutils.spigot.serialization;

import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import com.kabryxis.kabutils.data.file.yaml.serialization.SerializationType;
import com.kabryxis.kabutils.data.file.yaml.serialization.Serializer;
import com.kabryxis.kabutils.spigot.inventory.itemstack.ItemBuilder;

public class ItemBuilderSerializer implements Serializer {
	
	protected Class<?>[] classes = { ItemBuilder.class };
	
	@Override
	public String getPrefix() {
		return "ib";
	}
	
	@Override
	public SerializationType getType() {
		return SerializationType.SECTION;
	}
	
	@Override
	public Class<?>[] getClasses() {
		return classes;
	}
	
	@Override
	public Object deserialize(Object obj) {
		return new ItemBuilder((ConfigSection)obj);
	}
	
	@Override
	public Object serialize(Object obj) {
		return ((ItemBuilder)obj).serialize();
	}
	
}
