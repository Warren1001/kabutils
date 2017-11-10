package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R3;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class WrappedNBTTagCompoundv1_8_R3 extends WrappedNBTTagCompound<NBTTagCompound> {
	
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
		get().set(key, ((WrappedNBTTagListv1_8_R3)list).get());
	}
	
	@Override
	public void getList(WrappedNBTTagList<?> instance, String key, int i) {
		WrappedNBTTagListv1_8_R3 handle = (WrappedNBTTagListv1_8_R3)instance;
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
