package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl.WrappedNBTBasev1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_8_R3;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WrappedNBTTagCompoundv1_8_R3 extends WrappedNBTBasev1_8_R3 implements WrappedNBTTagCompound {
	
	private NBTTagCompound tag;
	
	public WrappedNBTTagCompoundv1_8_R3() {
		tag = null;
	}
	
	public WrappedNBTTagCompoundv1_8_R3(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedNBTTagCompoundv1_8_R3 setHandle(Object obj) {
		if(obj instanceof NBTTagCompound) tag = (NBTTagCompound)obj;
		else if(obj instanceof File) {
			try {
				tag = NBTCompressedStreamTools.a(new FileInputStream((File)obj));
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		else if(obj instanceof CraftItemStack) {
			try {
				tag = ((ItemStack)WrappedItemStack.FIELD_HANDLE.get(obj)).getTag();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	@Override
	public NBTTagCompound getHandle() {
		return tag;
	}
	
	@Override
	public void clear() {
		tag = null;
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
	public void set(String key, Object obj) {
		if(obj == null) remove(key);
		else if(obj instanceof NBTBase) tag.set(key, (NBTBase)obj);
		else if(obj instanceof WrappedNBTBasev1_8_R3) tag.set(key, ((WrappedNBTBasev1_8_R3)obj).getHandle());
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
	
	@Override
	public NBTBase getBaseRaw(String key) {
		return tag.get(key);
	}
	
	@Override
	public NBTTagCompound getCompoundRaw(String key) {
		return tag.getCompound(key);
	}
	
	@Override
	public WrappedNBTTagCompoundv1_8_R3 getCompound(String key, WrappedNBTTagCompound wrappedTag) {
		return ((WrappedNBTTagCompoundv1_8_R3)wrappedTag).setHandle(tag.getCompound(key));
	}
	
	@Override
	public WrappedNBTTagCompoundv1_8_R3 getCompound(String key) {
		return getCompound(key, new WrappedNBTTagCompoundv1_8_R3());
	}
	
	@Override
	public WrappedNBTTagListv1_8_R3 getList(String key, int i, WrappedNBTTagList list) {
		return ((WrappedNBTTagListv1_8_R3)list).setHandle(tag.getList(key, i));
	}
	
	@Override
	public WrappedNBTTagListv1_8_R3 getList(String key, int i) {
		return new WrappedNBTTagListv1_8_R3(tag.getList(key, i));
	}
	
	@Override
	public String getString(String key) {
		return tag.getString(key);
	}
	
	@Override
	public int getInt(String key) {
		return tag.getInt(key);
	}
	
	@Override
	public double getDouble(String key) {
		return tag.getDouble(key);
	}
	
	@Override
	public float getFloat(String key) {
		return tag.getFloat(key);
	}
	
	@Override
	public boolean getBoolean(String key) {
		return tag.getBoolean(key);
	}
	
	@Override
	public byte getByte(String key) {
		return tag.getByte(key);
	}
	
	@Override
	public long getLong(String key) {
		return tag.getLong(key);
	}
	
	@Override
	public short getShort(String key) {
		return tag.getShort(key);
	}
	
	@Override
	public int[] getIntArray(String key) {
		return tag.getIntArray(key);
	}
	
	@Override
	public byte[] getByteArray(String key) {
		return tag.getByteArray(key);
	}
	
	@Override
	public void remove(String key) {
		tag.remove(key);
	}
	
}
