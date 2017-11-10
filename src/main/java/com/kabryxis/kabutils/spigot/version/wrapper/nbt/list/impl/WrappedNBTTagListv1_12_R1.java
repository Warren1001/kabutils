package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import net.minecraft.server.v1_12_R1.NBTTagList;

public class WrappedNBTTagListv1_12_R1 extends WrappedNBTTagList<NBTTagList> {
	
	@Override
	public void newInstance() {
		set(new NBTTagList());
	}
	
	@Override
	public void add(WrappedNBTTagCompound<?> tag) {
		get().add(((WrappedNBTTagCompoundv1_12_R1)tag).get());
	}
	
	@Override
	public Object handleGet(int index) {
		return get().get(index);
	}
	
	@Override
	public int size() {
		return get().size();
	}
	
}
