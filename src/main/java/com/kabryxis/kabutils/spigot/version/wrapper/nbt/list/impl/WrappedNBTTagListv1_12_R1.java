package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl.WrappedNBTBasev1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class WrappedNBTTagListv1_12_R1 extends WrappedNBTBasev1_12_R1 implements WrappedNBTTagList {
	
	private NBTTagList list;
	
	public WrappedNBTTagListv1_12_R1(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public void setHandle(Object obj) {
		super.setHandle(obj);
		if(obj instanceof NBTTagList) list = (NBTTagList)obj;
		else if(obj instanceof Boolean) if((Boolean)obj) list = new NBTTagList();
	}
	
	@Override
	public NBTTagList getHandle() {
		return list;
	}
	
	@Override
	public void clear() {
		super.clear();
		list = null;
	}
	
	@Override
	public void add(WrappedNBTTagCompound tag) {
		list.add(((WrappedNBTTagCompoundv1_12_R1)tag).getHandle());
	}
	
	@Override
	public NBTTagCompound get(int index) {
		return list.get(index);
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
}
