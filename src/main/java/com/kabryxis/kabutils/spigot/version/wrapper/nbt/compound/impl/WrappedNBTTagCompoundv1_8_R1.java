package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.WrappedNBTBase;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl.WrappedNBTBasev1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R1;
import net.minecraft.server.v1_8_R1.NBTBase;
import net.minecraft.server.v1_8_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class WrappedNBTTagCompoundv1_8_R1 extends WrappedNBTBasev1_8_R1 implements WrappedNBTTagCompound {
	
	private NBTTagCompound tag;
	
	public WrappedNBTTagCompoundv1_8_R1(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public void setHandle(Object obj) {
		super.setHandle(obj);
		if(obj instanceof NBTTagCompound) tag = (NBTTagCompound)obj;
		else if(obj instanceof File) {
			try {
				tag = NBTCompressedStreamTools.a(new FileInputStream((File)obj));
			} catch(FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public NBTTagCompound getHandle() {
		return tag;
	}
	
	@Override
	public void clear() {
		super.clear();
		tag = null;
	}
	
	@Override
	public void savePlayerData(File playerFile) {
		try {
			NBTCompressedStreamTools.a(tag, new FileOutputStream(playerFile));
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void set(String key, Object obj) {
		if(obj == null) remove(key);
		else if(obj instanceof NBTBase) tag.set(key, (NBTBase)obj);
		else if(obj instanceof WrappedNBTBasev1_8_R1) tag.set(key, ((WrappedNBTBasev1_8_R1)obj).getHandle());
		else if(obj instanceof String) tag.setString(key, (String)obj);
		else if(obj instanceof Integer) tag.setInt(key, (Integer)obj);
		else if(obj instanceof Boolean) tag.setBoolean(key, (Boolean)obj);
		else if(obj instanceof Byte) tag.setByte(key, (Byte)obj);
		else if(obj instanceof byte[]) tag.setByteArray(key, (byte[])obj);
		else if(obj instanceof Double) tag.setDouble(key, (Double)obj);
		else if(obj instanceof Float) tag.setFloat(key, (Float)obj);
		else if(obj instanceof int[]) tag.setIntArray(key, (int[])obj);
		else if(obj instanceof Long) tag.setLong(key, (Long)obj);
		else if(obj instanceof Short) tag.setShort(key, (Short)obj);
		else throw new IllegalArgumentException("NBTTagCompound cannot hold " + obj.getClass().getSimpleName() + " types.");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		Object o;
		if(clazz == WrappedNBTTagList.class) throw new IllegalArgumentException("WrappedNBTTagCompound#getList must be called for retrieving NBTTagLists.");
		else if(clazz == WrappedNBTTagCompound.class) o = new WrappedNBTTagCompoundv1_8_R1(tag.getCompound(key));
		else if(clazz == WrappedNBTBase.class) o = new WrappedNBTBasev1_8_R1(tag.get(key));
		else if(clazz == String.class) o = tag.getString(key);
		else if(clazz == Integer.class || clazz == int.class) o = tag.getInt(key);
		else if(clazz == Boolean.class || clazz == boolean.class) o = tag.getBoolean(key);
		else if(clazz == Byte.class || clazz == byte.class) o = tag.getByte(key);
		else if(clazz == byte[].class) o = tag.getByteArray(key);
		else if(clazz == Double.class || clazz == double.class) o = tag.getDouble(key);
		else if(clazz == Float.class || clazz == float.class) o = tag.getFloat(key);
		else if(clazz == int[].class) o = tag.getIntArray(key);
		else if(clazz == Long.class || clazz == long.class) o = tag.getLong(key);
		else if(clazz == Short.class || clazz == short.class) o = tag.getShort(key);
		else throw new IllegalArgumentException("NBTTagCompound cannot hold " + clazz.getSimpleName() + " types.");
		return (T)o;
	}
	
	@Override
	public WrappedNBTTagListv1_8_R1 getList(String key, int i) {
		return new WrappedNBTTagListv1_8_R1(tag.getList(key, i));
	}
	
	@Override
	public void remove(String key) {
		tag.remove(key);
	}
	
}
