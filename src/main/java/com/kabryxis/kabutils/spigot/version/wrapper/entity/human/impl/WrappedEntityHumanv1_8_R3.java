package com.kabryxis.kabutils.spigot.version.wrapper.entity.human.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.human.WrappedEntityHuman;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class WrappedEntityHumanv1_8_R3 extends WrappedEntityHuman {
	
	/*private static final Field counterField;
	
	static {
		Field field = null;
		try {
			field = Entity.class.getField("entityCount");
			field.setAccessible(true);
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		}
		counterField = field;
	}
	
	private static int getCounterAndIncrease() {
		int count;
		try {
			count = counterField.getInt(null);
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return -1;
		}
		count++;
		try {
			counterField.setInt(null, count);
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		return count;
	}*/
	
	private EntityHuman handle;
	
	public WrappedEntityHumanv1_8_R3(Player player) {
		CraftPlayer craftPlayer = (CraftPlayer)player;
		handle = new EntityHuman(((CraftWorld)player.getWorld()).getHandle(), craftPlayer.getProfile()) {
			
			/*@Override
			public void sendMessage(IChatBaseComponent ichatbasecomponent) {}
			
			@Override
			public boolean a(int i, String s) {
				return false;
			}
			
			@Override
			public BlockPosition getChunkCoordinates() {
				return null;
			}*/
			
			@Override
			public boolean isSpectator() {
				return false;
			}
			
		};
		//handle.d(getCounterAndIncrease());
		handle.getDataWatcher().watch(10, craftPlayer.getHandle().getDataWatcher().getByte(10));
	}
	
	@Override
	public Object getObject() {
		return handle;
	}
	
	@Override
	public Object getDataWatcher() {
		return handle.getDataWatcher();
	}
	
	@Override
	public HumanEntity getBukkitEntity() {
		return handle.getBukkitEntity();
	}
	
}
