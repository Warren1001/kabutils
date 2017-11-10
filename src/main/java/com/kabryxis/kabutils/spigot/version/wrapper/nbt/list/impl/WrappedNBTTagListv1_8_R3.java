package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import net.minecraft.server.v1_8_R3.NBTTagList;

public class WrappedNBTTagListv1_8_R3 extends WrappedNBTTagList<NBTTagList> {
	
	@Override
	public void newInstance() {
		set(new NBTTagList());
	}
	
	@Override
	public void add(WrappedNBTTagCompound<?> tag) {
		get().add(((WrappedNBTTagCompoundv1_8_R3)tag).get());
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
