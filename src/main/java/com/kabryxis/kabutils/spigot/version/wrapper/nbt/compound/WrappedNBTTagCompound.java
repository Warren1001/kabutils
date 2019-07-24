package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import java.io.File;

public interface WrappedNBTTagCompound extends WrappedNBTBase {
	
	Class<WrappedNBTTagCompoundv1_8_R1> v1_8_R1 = WrappedNBTTagCompoundv1_8_R1.class;
	Class<WrappedNBTTagCompoundv1_8_R2> v1_8_R2 = WrappedNBTTagCompoundv1_8_R2.class;
	Class<WrappedNBTTagCompoundv1_8_R3> v1_8_R3 = WrappedNBTTagCompoundv1_8_R3.class;
	Class<WrappedNBTTagCompoundv1_9_R1> v1_9_R1 = WrappedNBTTagCompoundv1_9_R1.class;
	Class<WrappedNBTTagCompoundv1_9_R2> v1_9_R2 = WrappedNBTTagCompoundv1_9_R2.class;
	Class<WrappedNBTTagCompoundv1_10_R1> v1_10_R1 = WrappedNBTTagCompoundv1_10_R1.class;
	Class<WrappedNBTTagCompoundv1_11_R1> v1_11_R1 = WrappedNBTTagCompoundv1_11_R1.class;
	Class<WrappedNBTTagCompoundv1_12_R1> v1_12_R1 = WrappedNBTTagCompoundv1_12_R1.class;
	Class<WrappedNBTTagCompoundv1_13_R2> v1_13_R2 = WrappedNBTTagCompoundv1_13_R2.class;
	Class<WrappedNBTTagCompoundv1_14_R1> v1_14_R1 = WrappedNBTTagCompoundv1_14_R1.class;
	
	static WrappedNBTTagCompound newInstance() {
		return WrapperFactory.getSupplier(WrappedNBTTagCompound.class, Object.class).apply((Object)null);
	}
	
	@Override
	WrappedNBTTagCompound setHandle(Object obj);
	
	void savePlayerData(File playerFile);
	
	void set(String key, Object obj);
	
	Object getBaseRaw(String key);
	
	Object getCompoundRaw(String key);
	
	WrappedNBTTagCompound getCompound(String key, WrappedNBTTagCompound wrappedTag);
	
	WrappedNBTTagCompound getCompound(String key);
	
	WrappedNBTTagList getList(String key, int i, WrappedNBTTagList list);
	
	WrappedNBTTagList getList(String key, int i);
	
	String getString(String key);
	
	int getInt(String key);
	
	double getDouble(String key);
	
	float getFloat(String key);
	
	boolean getBoolean(String key);
	
	byte getByte(String key);
	
	long getLong(String key);
	
	short getShort(String key);
	
	int[] getIntArray(String key);
	
	byte[] getByteArray(String key);
	
	void remove(String key);
	
}
