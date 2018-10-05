package com.kabryxis.kabutils.spigot.version.custom.player.impl;

import com.kabryxis.kabutils.spigot.version.custom.player.ItemStackForwarder;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class WrappedPlayerInventoryv1_8_R3 extends PlayerInventory {
	
	private final ItemStackForwarder forwarder;
	
	public WrappedPlayerInventoryv1_8_R3(Player player, ItemStackForwarder forwarder) {
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
		this.forwarder = forwarder;
	}
	
	@Override
	public void setItem(int index, ItemStack item) {
		super.setItem(index, item);
		forward();
	}
	
	@Override
	public boolean pickup(ItemStack item) {
		if(item != null && item.count != 0 && item.getItem() != null) {
			try {
				int i;
				if(item.g()) {
					i = getFirstEmptySlotIndex();
					if(i >= 0) {
						items[i] = ItemStack.b(item);
						items[i].c = 5;
						item.count = 0;
						forward();
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
		ItemStack item = super.splitStack(i, j);
		forward();
		return item;
	}
	
	@Override
	public ItemStack splitWithoutUpdate(int i) {
		ItemStack item = super.splitWithoutUpdate(i);
		forward();
		return item;
	}
	
	@Override
	public void b(PlayerInventory inventory) {
		super.b(inventory);
		forward();
	}
	
	public void forward() {
		Set<org.bukkit.inventory.ItemStack> itemsToForward = new HashSet<>();
		for(ItemStack item : items) {
			if(item != null && Item.getId(item.getItem()) != 0) itemsToForward.add(CraftItemStack.asCraftMirror(item));
		}
		for(ItemStack item : armor) {
			if(item != null && Item.getId(item.getItem()) != 0) itemsToForward.add(CraftItemStack.asCraftMirror(item));
		}
		forwarder.forward(itemsToForward);
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
				forward();
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
