package com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import net.minecraft.server.v1_8_R2.NBTBase;

public class WrappedNBTBasev1_8_R2 implements WrappedNBTBase {
	
	private NBTBase handle;
	
	public WrappedNBTBasev1_8_R2() {}
	
	public WrappedNBTBasev1_8_R2(NBTBase base) {
		this.handle = base;
	}
	
	@Override
	public WrappedNBTBasev1_8_R2 setHandle(Object obj) {
		if(obj instanceof NBTBase) handle = (NBTBase)obj;
		return this;
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
