package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.*;

public interface WrappedNBTTagList extends WrappedNBTBase {
	
	Class<WrappedNBTTagListv1_8_R1> v1_8_R1 = WrappedNBTTagListv1_8_R1.class;
	Class<WrappedNBTTagListv1_8_R2> v1_8_R2 = WrappedNBTTagListv1_8_R2.class;
	Class<WrappedNBTTagListv1_8_R3> v1_8_R3 = WrappedNBTTagListv1_8_R3.class;
	Class<WrappedNBTTagListv1_9_R1> v1_9_R1 = WrappedNBTTagListv1_9_R1.class;
	Class<WrappedNBTTagListv1_9_R2> v1_9_R2 = WrappedNBTTagListv1_9_R2.class;
	Class<WrappedNBTTagListv1_10_R1> v1_10_R1 = WrappedNBTTagListv1_10_R1.class;
	Class<WrappedNBTTagListv1_11_R1> v1_11_R1 = WrappedNBTTagListv1_11_R1.class;
	Class<WrappedNBTTagListv1_12_R1> v1_12_R1 = WrappedNBTTagListv1_12_R1.class;
	Class<WrappedNBTTagListv1_13_R2> v1_13_R2 = WrappedNBTTagListv1_13_R2.class;
	Class<WrappedNBTTagListv1_14_R1> v1_14_R1 = WrappedNBTTagListv1_14_R1.class;
	Class<WrappedNBTTagListv1_15_R1> v1_15_R1 = WrappedNBTTagListv1_15_R1.class;
	
	static WrappedNBTTagList newInstance() {
		return newInstance(false);
	}
	
	static WrappedNBTTagList newInstance(boolean newEmpty) {
		return WrapperFactory.getSupplier(WrappedNBTTagList.class, Object.class).apply(newEmpty);
	}
	
	@Override
	WrappedNBTTagList setHandle(Object obj);
	
	void add(WrappedNBTTagCompound tag);
	
	Object get(int index);
	
	int size();
	
}
