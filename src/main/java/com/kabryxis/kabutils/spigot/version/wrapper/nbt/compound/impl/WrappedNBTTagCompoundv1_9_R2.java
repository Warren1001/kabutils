package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_9_R2;
import net.minecraft.server.v1_9_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WrappedNBTTagCompoundv1_9_R2 extends WrappedNBTTagCompound {
	
	private final NBTTagCompound tag;
	
	public WrappedNBTTagCompoundv1_9_R2() {
		this.tag = new NBTTagCompound();
	}
	
	public WrappedNBTTagCompoundv1_9_R2(NBTTagCompound tag) {
		this.tag = tag;
	}
	
	public WrappedNBTTagCompoundv1_9_R2(File playerFile) {
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
		tag.set(key, ((WrappedNBTTagListv1_9_R2)list).getHandle());
	}
	
	@Override
	public WrappedNBTTagListv1_9_R2 getList(String key, int i) {
		return new WrappedNBTTagListv1_9_R2(tag.getList(key, i));
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
