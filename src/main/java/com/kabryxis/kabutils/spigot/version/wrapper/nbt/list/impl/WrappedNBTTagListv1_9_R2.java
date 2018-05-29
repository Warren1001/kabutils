package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_9_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import net.minecraft.server.v1_9_R2.NBTTagList;

public class WrappedNBTTagListv1_9_R2 extends WrappedNBTTagList {
	
	private final NBTTagList list;
	
	public WrappedNBTTagListv1_9_R2() {
		this.list = new NBTTagList();
	}
	
	public WrappedNBTTagListv1_9_R2(NBTTagList list) {
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
		list.add(((WrappedNBTTagCompoundv1_9_R2)tag).getHandle());
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
