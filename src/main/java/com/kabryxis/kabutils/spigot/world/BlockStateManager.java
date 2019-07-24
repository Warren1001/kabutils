package com.kabryxis.kabutils.spigot.world;

import com.kabryxis.kabutils.data.Maths;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.HashMap;
import java.util.Map;

public class BlockStateManager {

	private final Map<Block, BlockState> cachedStates = new HashMap<>();

	private final World world;

	public BlockStateManager(World world) {
		this.world = world;
	}

	public BlockState createState(Block block) {
		return cachedStates.computeIfAbsent(block, this::getTrueBlockState);
	}

	public BlockState createState(double x, double y, double z) {
		return createState(getBlock(x, y, z));
	}

	public BlockState createStateForce(Block block) {
		BlockState state = getTrueBlockState(block);
		cachedStates.put(block, state);
		return state;
	}

	public BlockState createStateForce(double x, double y, double z) {
		return createStateForce(getBlock(x, y, z));
	}

	public BlockState getState(Block block) {
		return cachedStates.get(block);
	}

	public BlockState getState(double x, double y, double z) {
		return getState(getBlock(x, y, z));
	}

	public BlockState clearState(Block block) {
		return cachedStates.remove(block);
	}

	public BlockState clearState(double x, double y, double z) {
		return clearState(getBlock(x, y, z));
	}
	
	public void revertState(Block block) {
		getState(block).update(true, false);
	}
	
	public void revertState(double x, double y, double z) {
	    revertState(getBlock(x, y, z));
    }

	public void setBlock(Block block, Material type, byte data) {
		block.setTypeIdAndData(type.getId(), data, false);
	}

	protected BlockState getTrueBlockState(Block block) {
		return block.getState();
	}

	protected Block getBlock(double x, double y, double z) {
		return world.getBlockAt(Maths.floor(x), Maths.floor(y), Maths.floor(z));
	}

}
