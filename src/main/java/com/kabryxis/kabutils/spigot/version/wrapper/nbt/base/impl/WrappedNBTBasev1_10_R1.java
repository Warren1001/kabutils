package com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import net.minecraft.server.v1_10_R1.NBTBase;

public class WrappedNBTBasev1_10_R1 implements WrappedNBTBase {
	
	private NBTBase handle;
	
	public WrappedNBTBasev1_10_R1() {}
	
	public WrappedNBTBasev1_10_R1(NBTBase base) {
		this.handle = base;
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof NBTBase) handle = (NBTBase)obj;
	}
	
	@Override
	public NBTBase getHandle() {
		return handle;
	}
	
	@Override
	public void clear() {
		handle = null;
	}
	
}
