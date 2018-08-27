package com.kabryxis.kabutils.spigot.world.schematic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BlockSelection {
	
	private final Set<Block> selectedBlocks = new HashSet<>();
	
	private Player player;
	private int leftX, leftY, leftZ;
	private int rightX, rightY, rightZ;
	
	private int lowestX, highestX, lowestY, highestY, lowestZ, highestZ;
	
	public BlockSelection(Player player) {
		this.player = player;
		//reset();
	}
	
	public void tpToLowest() {
		player.teleport(new Location(player.getWorld(), lowestX, lowestY, lowestZ));
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Set<Block> getBlocks() {
		return selectedBlocks;
	}
	
	public void setLeft(int x, int y, int z) {
		leftX = x;
		leftY = y;
		leftZ = z;
	}
	
	public void setLeft(Location loc) {
		setLeft(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public void setRight(int x, int y, int z) {
		rightX = x;
		rightY = y;
		rightZ = z;
	}
	
	public void setRight(Location loc) {
		setRight(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public int getLowestX() {
		return lowestX;
	}
	
	public int getHighestX() {
		return highestX;
	}
	
	public int getLowestY() {
		return lowestY;
	}
	
	public int getHighestY() {
		return highestY;
	}
	
	public int getLowestZ() {
		return lowestZ;
	}
	
	public int getHighestZ() {
		return highestZ;
	}
	
	public void addSelection(boolean air) {
		World world = player.getWorld();
		extreme();
		for(int x = leftX; x <= rightX; x++) {
			for(int y = leftY; y <= rightY; y++) {
				for(int z = leftZ; z <= rightZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					if(air || block.getType() != Material.AIR) addBlock(block);
				}
			}
		}
	}
	
	public void removeSelection() {
		World world = player.getWorld();
		extreme();
		for(int x = lowestX; x <= highestX; x++) {
			for(int y = lowestY; y <= highestY; y++) {
				for(int z = lowestZ; z <= highestZ; z++) {
					selectedBlocks.remove(world.getBlockAt(x, y, z));
				}
			}
		}
		//recalculateMinMax();
	}
	
	public void extreme() {
		/*if(rightX < leftX) {
			int buffer = leftX;
			leftX = rightX;
			rightX = buffer;
		}
		if(rightY < leftY) {
			int buffer = leftY;
			leftY = rightY;
			rightY = buffer;
		}
		if(rightZ < leftZ) {
			int buffer = leftZ;
			leftZ = rightZ;
			rightZ = buffer;
		}*/
		lowestX = Math.min(rightX, leftX);
		highestX = Math.max(rightX, leftX);
		lowestY = Math.min(rightY, leftY);
		highestY = Math.max(rightY, leftY);
		lowestZ = Math.min(rightZ, leftZ);
		highestZ = Math.max(rightZ, leftZ);
	}
	
	public void addBlock(Block block) {
		if(!selectedBlocks.contains(block)) {
			selectedBlocks.add(block);
			//calculateMinMax(block);
		}
	}
	
	private void calculateMinMax(Block block) {
		int x = block.getX(), y = block.getY(), z = block.getZ();
		if(x < lowestX) lowestX = x;
		if(x > highestX) highestX = x;
		if(y < lowestY) lowestY = y;
		if(y > highestY) highestY = y;
		if(z < lowestZ) lowestZ = z;
		if(z > highestZ) highestZ = z;
	}
	
	public void removeBlock(Block block) {
		selectedBlocks.remove(block);
		int y = block.getY();
		//if(y == lowestY || y == highestY) recalculateMinMax();
	}
	
	private void reset() {
		lowestX = Integer.MAX_VALUE;
		highestX = Integer.MIN_VALUE;
		lowestY = 256;
		highestY = 0;
		lowestZ = Integer.MAX_VALUE;
		highestZ = Integer.MIN_VALUE;
	}
	
	public void recalculateMinMax() {
		reset();
		//selectedBlocks.forEach(this::calculateMinMax);
	}
	
	public void clearSelection() {
		selectedBlocks.clear();
		//reset();
	}
	
}
