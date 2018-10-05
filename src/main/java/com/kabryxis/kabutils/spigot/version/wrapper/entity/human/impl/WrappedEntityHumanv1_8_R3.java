package com.kabryxis.kabutils.spigot.version.wrapper.entity.human.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.human.WrappedEntityHuman;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class WrappedEntityHumanv1_8_R3 implements WrappedEntityHuman {
	
	private EntityHuman handle;
	
	public WrappedEntityHumanv1_8_R3(Player player) {
		setHandle(player);
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof EntityHuman) handle = (EntityHuman)obj;
		else if(obj instanceof Player) {
			CraftPlayer craftPlayer = (CraftPlayer)obj;
			handle = new EntityHuman(((CraftWorld)craftPlayer.getWorld()).getHandle(), craftPlayer.getProfile()) {
				
				@Override
				public boolean isSpectator() {
					return false;
				}
				
			};
			handle.getDataWatcher().watch(10, craftPlayer.getHandle().getDataWatcher().getByte(10));
		}
	}
	
	@Override
	public EntityHuman getHandle() {
		return handle;
	}
	
	@Override
	public DataWatcher getDataWatcher() {
		return handle.getDataWatcher();
	}
	
	@Override
	public HumanEntity getBukkitEntity() {
		return handle.getBukkitEntity();
	}
	
}
