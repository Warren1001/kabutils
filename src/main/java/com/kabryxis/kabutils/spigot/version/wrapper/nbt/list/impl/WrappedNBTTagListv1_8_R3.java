package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import net.minecraft.server.v1_8_R3.NBTTagList;

public class WrappedNBTTagListv1_8_R3 extends WrappedNBTTagList {
	
	private final NBTTagList list;
	
	public WrappedNBTTagListv1_8_R3() {
		this.list = new NBTTagList();
	}
	
	public WrappedNBTTagListv1_8_R3(NBTTagList list) {
		this.list = list;
	}
	
	public NBTTagList getHandle() {
		return list;
	}
	
	@Override
	public Object getObject() {
		return list;
	}
	
	@Override
	public void add(WrappedNBTTagCompound tag) {
		list.add(((WrappedNBTTagCompoundv1_8_R3)tag).getHandle());
	}
	
	@Override
	public Object handleGet(int index) {
		return list.get(index);
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
}
