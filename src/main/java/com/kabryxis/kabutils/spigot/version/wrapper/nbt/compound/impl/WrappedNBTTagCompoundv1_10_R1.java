package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_10_R1;
import net.minecraft.server.v1_10_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WrappedNBTTagCompoundv1_10_R1 extends WrappedNBTTagCompound {
	
	private final NBTTagCompound tag;
	
	public WrappedNBTTagCompoundv1_10_R1() {
		this.tag = new NBTTagCompound();
	}
	
	public WrappedNBTTagCompoundv1_10_R1(NBTTagCompound tag) {
		this.tag = tag;
	}
	
	public WrappedNBTTagCompoundv1_10_R1(File playerFile) {
		NBTTagCompound tag = null;
		try {
			tag = NBTCompressedStreamTools.a(new FileInputStream(playerFile));
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.tag = tag;
	}
	
	public NBTTagCompound getHandle() {
		return tag;
	}
	
	@Override
	public Object getObject() {
		return tag;
	}
	
	@Override
	public void savePlayerData(File playerFile) {
		try {
			NBTCompressedStreamTools.a(tag, new FileOutputStream(playerFile));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void set(String key, WrappedNBTTagList list) {
		tag.set(key, ((WrappedNBTTagListv1_10_R1)list).getHandle());
	}
	
	@Override
	public WrappedNBTTagListv1_10_R1 getList(String key, int i) {
		return new WrappedNBTTagListv1_10_R1(tag.getList(key, i));
	}
	
	@Override
	public void setByte(String key, byte value) {
		tag.setByte(key, value);
	}
	
	@Override
	public byte getByte(String key) {
		return tag.getByte(key);
	}
	
}
