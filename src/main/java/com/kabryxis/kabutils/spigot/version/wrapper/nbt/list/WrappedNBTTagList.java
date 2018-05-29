package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.*;

import java.util.function.Supplier;

public abstract class WrappedNBTTagList implements Wrappable {
	
	private static final Supplier<WrappedNBTTagList> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedNBTTagListv1_8_R1::new;
				break;
			case v1_8_R2:
				supplier = WrappedNBTTagListv1_8_R2::new;
				break;
			case v1_8_R3:
				supplier = WrappedNBTTagListv1_8_R3::new;
				break;
			case v1_9_R1:
				supplier = WrappedNBTTagListv1_9_R1::new;
				break;
			case v1_9_R2:
				supplier = WrappedNBTTagListv1_9_R2::new;
				break;
			case v1_10_R1:
				supplier = WrappedNBTTagListv1_10_R1::new;
				break;
			case v1_11_R1:
				supplier = WrappedNBTTagListv1_11_R1::new;
				break;
			case v1_12_R1:
				supplier = WrappedNBTTagListv1_12_R1::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedNBTTagList newInstance() {
		return supplier.get();
	}
	
	public abstract void add(WrappedNBTTagCompound tag);
	
	public abstract Object handleGet(int index);
	
	public abstract int size();
	
}
