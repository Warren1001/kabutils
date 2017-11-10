package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_11_R1;

import net.minecraft.server.v1_11_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class WrappedNBTTagCompoundv1_11_R1 extends WrappedNBTTagCompound<NBTTagCompound> {
	
	@Override
	public void newInstance() {
		set(new NBTTagCompound());
	}
	
	@Override
	public void loadPlayerData(File file) {
		try {
			set(NBTCompressedStreamTools.a(new FileInputStream(file)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void savePlayerData(File file) {
		try {
			NBTCompressedStreamTools.a(get(), new FileOutputStream(file));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void set(String key, WrappedNBTTagList<?> list) {
		get().set(key, ((WrappedNBTTagListv1_11_R1)list).get());
	}
	
	@Override
	public void getList(WrappedNBTTagList<?> instance, String key, int i) {
		WrappedNBTTagListv1_11_R1 handle = (WrappedNBTTagListv1_11_R1)instance;
		handle.set(get().getList(key, i));
	}
	
	@Override
	public void setByte(String key, byte value) {
		get().setByte(key, value);
	}
	
	@Override
	public byte getByte(String key) {
		return get().getByte(key);
	}
	
}
