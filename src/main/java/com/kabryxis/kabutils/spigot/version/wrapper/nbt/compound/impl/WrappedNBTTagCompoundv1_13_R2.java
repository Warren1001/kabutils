package com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.base.impl.WrappedNBTBasev1_13_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.impl.WrappedNBTTagListv1_13_R2;
import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WrappedNBTTagCompoundv1_13_R2 extends WrappedNBTBasev1_13_R2 implements WrappedNBTTagCompound {
	
	private NBTTagCompound tag;
	
	public WrappedNBTTagCompoundv1_13_R2() {
		tag = null;
	}
	
	public WrappedNBTTagCompoundv1_13_R2(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedNBTTagCompoundv1_13_R2 setHandle(Object obj) {
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
		else if(obj instanceof WrappedNBTBasev1_13_R2) tag.set(key, ((WrappedNBTBasev1_13_R2)obj).getHandle());
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
	public WrappedNBTTagCompoundv1_13_R2 getCompound(String key, WrappedNBTTagCompound wrappedTag) {
		return ((WrappedNBTTagCompoundv1_13_R2)wrappedTag).setHandle(tag.getCompound(key));
	}
	
	@Override
	public WrappedNBTTagCompoundv1_13_R2 getCompound(String key) {
		return getCompound(key, new WrappedNBTTagCompoundv1_13_R2());
	}
	
	@Override
	public WrappedNBTTagListv1_13_R2 getList(String key, int i, WrappedNBTTagList list) {
		return ((WrappedNBTTagListv1_13_R2)list).setHandle(tag.getList(key, i));
	}
	
	@Override
	public WrappedNBTTagListv1_13_R2 getList(String key, int i) {
		return new WrappedNBTTagListv1_13_R2(tag.getList(key, i));
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
