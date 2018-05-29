package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import java.io.File;
import java.util.function.Supplier;

public abstract class WrappedNBTTagCompound implements Wrappable {
	
	private static final Supplier<WrappedNBTTagCompound> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedNBTTagCompoundv1_8_R1::new;
				break;
			case v1_8_R2:
				supplier = WrappedNBTTagCompoundv1_8_R2::new;
				break;
			case v1_8_R3:
				supplier = WrappedNBTTagCompoundv1_8_R3::new;
				break;
			case v1_9_R1:
				supplier = WrappedNBTTagCompoundv1_9_R1::new;
				break;
			case v1_9_R2:
				supplier = WrappedNBTTagCompoundv1_9_R2::new;
				break;
			case v1_10_R1:
				supplier = WrappedNBTTagCompoundv1_10_R1::new;
				break;
			case v1_11_R1:
				supplier = WrappedNBTTagCompoundv1_11_R1::new;
				break;
			case v1_12_R1:
				supplier = WrappedNBTTagCompoundv1_12_R1::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedNBTTagCompound newInstance() {
		return supplier.get();
	}
	
	public abstract void savePlayerData(File playerFile);
	
	public abstract void set(String key, WrappedNBTTagList list);
	
	public abstract WrappedNBTTagList getList(String key, int i);
	
	public abstract void setByte(String key, byte value);
	
	public abstract byte getByte(String key);
	
}
