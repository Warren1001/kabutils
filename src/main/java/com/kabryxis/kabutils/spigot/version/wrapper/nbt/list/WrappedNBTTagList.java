package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrapper;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_9_R2;

public abstract class WrappedNBTTagList<T> extends Wrapper<T> {
	
	static { // include in maven shade plugin
		WrappedNBTTagListv1_8_R1.class.getClass();
		WrappedNBTTagListv1_8_R2.class.getClass();
		WrappedNBTTagListv1_8_R3.class.getClass();
		WrappedNBTTagListv1_9_R1.class.getClass();
		WrappedNBTTagListv1_9_R2.class.getClass();
		WrappedNBTTagListv1_10_R1.class.getClass();
		WrappedNBTTagListv1_11_R1.class.getClass();
		WrappedNBTTagListv1_12_R1.class.getClass();
	}
	
	public abstract void newInstance();
	
	public abstract void add(WrappedNBTTagCompound<?> tag);
	
	public abstract Object handleGet(int index);
	
	public abstract int size();
	
}
