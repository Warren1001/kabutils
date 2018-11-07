package com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import net.minecraft.server.v1_8_R3.NBTBase;

public class WrappedNBTBasev1_8_R3 implements WrappedNBTBase {
	
	private NBTBase handle;
	
	public WrappedNBTBasev1_8_R3() {}
	
	public WrappedNBTBasev1_8_R3(NBTBase base) {
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
