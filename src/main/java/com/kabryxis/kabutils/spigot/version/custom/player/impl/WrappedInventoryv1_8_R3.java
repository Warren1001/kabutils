package com.kabryxis.kabutils.spigot.version.custom.player.impl;

import com.kabryxis.kabutils.data.Arrays;
import com.kabryxis.kabutils.spigot.version.custom.player.ItemStackTracker;
import com.kabryxis.kabutils.spigot.version.custom.player.WrappedInventory;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class WrappedInventoryv1_8_R3 extends PlayerInventory implements WrappedInventory {
	
	private final ItemStackTracker tracker;
	
	public WrappedInventoryv1_8_R3(Player player, ItemStackTracker tracker) {
		super(((CraftHumanEntity)player).getHandle());
		CraftHumanEntity craftHumanEntity = ((CraftHumanEntity)player);
		EntityHuman entityHuman = craftHumanEntity.getHandle();
		this.items = entityHuman.inventory.items;
		this.armor = entityHuman.inventory.armor;
		this.e = entityHuman.inventory.e;
		this.itemInHandIndex = entityHuman.inventory.itemInHandIndex;
		this.transaction = entityHuman.inventory.transaction;
		entityHuman.inventory = this;
		entityHuman.defaultContainer = new ContainerPlayer(entityHuman.inventory, !entityHuman.world.isClientSide, entityHuman);
		entityHuman.activeContainer = entityHuman.defaultContainer;
		craftHumanEntity.setHandle(entityHuman);
		this.tracker = tracker;
	}
	
	public void track(ItemStack item) {
		if(item != null && Item.getId(item.getItem()) != 0) tracker.track(CraftItemStack.asCraftMirror(item));
	}
	
	public void untrack(ItemStack item) {
		if(item != null && Item.getId(item.getItem()) != 0) tracker.untrack(CraftItemStack.asCraftMirror(item));
	}
	
	public void reset() {
		tracker.untrackAll();
		Arrays.forEach(items, this::track);
		Arrays.forEach(armor, this::track);
	}
	
	@Override
	public void setItem(int index, ItemStack item) {
		untrack(getItem(index));
		super.setItem(index, item);
		track(item);
	}
	
	@Override
	public boolean pickup(ItemStack item) {
		if(item != null && item.count != 0 && item.getItem() != null) {
			try {
				int i;
				if(item.g()) {
					i = getFirstEmptySlotIndex();
					if(i >= 0) {
						untrack(items[i]);
						items[i] = ItemStack.b(item);
						items[i].c = 5;
						item.count = 0;
						track(items[i]);
						return true;
					}
					else if(player.abilities.canInstantlyBuild) {
						item.count = 0;
						return true;
					}
					return false;
				}
				else {
					do {
						i = item.count;
						item.count = attemptToAdd(item);
					} while(item.count > 0 && item.count < i);
					if(item.count == i && player.abilities.canInstantlyBuild) {
						item.count = 0;
						return true;
					}
					return item.count < i;
				}
			} catch(Throwable throwable) {
				CrashReport crashReport = CrashReport.a(throwable, "Adding item to inventory");
				CrashReportSystemDetails crashReportSystemDetails = crashReport.a("Item being added");
				crashReportSystemDetails.a("Item ID", Item.getId(item.getItem()));
				crashReportSystemDetails.a("Item data", item.getData());
				crashReportSystemDetails.a("Item name", item::getName);
				throw new ReportedException(crashReport);
			}
		}
		return false;
	}
	
	@Override
	public ItemStack splitStack(int i, int j) {
		ItemStack[] aitemstack = this.items;
		if (i >= this.items.length) {
			aitemstack = this.armor;
			i -= this.items.length;
		}
		
		if (aitemstack[i] != null) {
			ItemStack itemstack;
			if (aitemstack[i].count <= j) {
				itemstack = aitemstack[i];
				tracker.untrack(CraftItemStack.asCraftMirror(itemstack));
				aitemstack[i] = null;
				return itemstack;
			} else {
				itemstack = aitemstack[i].cloneAndSubtract(j);
				if (aitemstack[i].count == 0) {
					untrack(itemstack);
					aitemstack[i] = null;
				}
				
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public ItemStack splitWithoutUpdate(int i) {
		ItemStack[] aitemstack = this.items;
		if (i >= this.items.length) {
			aitemstack = this.armor;
			i -= this.items.length;
		}
		
		if (aitemstack[i] != null) {
			ItemStack itemstack = aitemstack[i];
			untrack(itemstack);
			aitemstack[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	
	@Override
	public void b(PlayerInventory inventory) {
		super.b(inventory);
		reset();
	}
	
	public int firstPartial(ItemStack item) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && items[i].getItem() == item.getItem() && items[i].isStackable() && items[i].count < items[i].getMaxStackSize() &&
					items[i].count < getMaxStackSize() && (!items[i].usesData() || items[i].getData() == item.getData()) && ItemStack.equals(items[i], item)) {
				return i;
			}
		}
		return -1;
	}
	
	public int attemptToAdd(ItemStack itemStack) {
		Item item = itemStack.getItem();
		int i = itemStack.count;
		int slot = firstPartial(itemStack);
		if(slot < 0) slot = getFirstEmptySlotIndex();
		if(slot < 0) return i;
		else {
			if(items[slot] == null) {
				items[slot] = new ItemStack(item, 0, itemStack.getData());
				if(itemStack.hasTag()) items[slot].setTag((NBTTagCompound)itemStack.getTag().clone());
				track(items[slot]);
			}
			int k = i;
			if(i > items[slot].getMaxStackSize() - items[slot].count) k = items[slot].getMaxStackSize() - items[slot].count;
			if(k > getMaxStackSize() - items[slot].count) k = getMaxStackSize() - items[slot].count;
			if(k == 0) return i;
			else {
				i -= k;
				items[slot].count += k;
				items[slot].c = 5;
				return i;
			}
		}
	}
	
}
