package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound;

import java.io.File;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_9_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

public abstract class WrappedNBTTagCompound<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedNBTTagCompoundv1_8_R1.class.getClass();
		WrappedNBTTagCompoundv1_8_R2.class.getClass();
		WrappedNBTTagCompoundv1_8_R3.class.getClass();
		WrappedNBTTagCompoundv1_9_R1.class.getClass();
		WrappedNBTTagCompoundv1_9_R2.class.getClass();
		WrappedNBTTagCompoundv1_10_R1.class.getClass();
		WrappedNBTTagCompoundv1_11_R1.class.getClass();
		WrappedNBTTagCompoundv1_12_R1.class.getClass();
	}
	
	public abstract void newInstance();
	
	public abstract void loadPlayerData(File file);
	
	public abstract void savePlayerData(File file);
	
	public abstract void set(String key, WrappedNBTTagList<?> list);
	
	public abstract void getList(WrappedNBTTagList<?> instance, String key, int i);
	
	public abstract void setByte(String key, byte value);
	
	public abstract byte getByte(String key);
	
}
