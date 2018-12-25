package com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl.WrappedNBTBasev1_13_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_13_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagList;

public class WrappedNBTTagListv1_13_R2 extends WrappedNBTBasev1_13_R2 implements WrappedNBTTagList {
	
	private NBTTagList list;
	
	public WrappedNBTTagListv1_13_R2() {}
	
	public WrappedNBTTagListv1_13_R2(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedNBTTagListv1_13_R2 setHandle(Object obj) {
		if(obj instanceof NBTTagList) list = (NBTTagList)obj;
		else if(obj instanceof Boolean && (Boolean)obj) list = new NBTTagList();
		return this;
	}
	
	@Override
	public NBTTagList getHandle() {
		return list;
	}
	
	@Override
	public void clear() {
		list = null;
	}
	
	@Override
	public void add(WrappedNBTTagCompound tag) {
		list.add(((WrappedNBTTagCompoundv1_13_R2)tag).getHandle());
	}
	
	@Override
	public NBTTagCompound get(int index) {
		return list.getCompound(index);
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
}
