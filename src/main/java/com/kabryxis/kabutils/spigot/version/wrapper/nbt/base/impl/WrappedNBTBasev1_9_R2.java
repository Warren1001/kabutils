package com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import net.minecraft.server.v1_9_R2.NBTBase;

public class WrappedNBTBasev1_9_R2 implements WrappedNBTBase {
	
	private NBTBase handle;
	
	public WrappedNBTBasev1_9_R2() {}
	
	public WrappedNBTBasev1_9_R2(NBTBase base) {
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
