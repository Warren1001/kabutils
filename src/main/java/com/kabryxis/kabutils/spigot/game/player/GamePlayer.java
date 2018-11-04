package com.kabryxis.kabutils.spigot.game.player;

import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class GamePlayer {
	
	public static final Set<Material> IGNORED_TARGET_BLOCK_MATERIALS = Sets.newHashSet(Material.AIR, Material.CARPET);
	
	protected final UUID uuid;
	
	public GamePlayer(UUID uuid) {
		this.uuid = uuid;
	}
	
	protected Player player;
	
	public void updatePlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public void reset(ResetFlag... flags) {
		for(ResetFlag flag : flags) {
			flag.reset(this);
		}
	}
	
	public void resetAll() {
		reset(ResetFlag.values());
	}
	
	public void reset(Location spawn, ResetFlag... flags) {
		teleport(spawn);
		reset(flags);
	}
	
	public void resetAll(Location spawn) {
		reset(spawn, ResetFlag.values());
	}
	
	public void hide() {
		Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));
	}
	
	public void sendMessage(String message, Object... objs) {
		sendMessage(String.format(message, objs));
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GamePlayer && ((GamePlayer)obj).uuid.equals(uuid);
	}
	
	// common methods from bukkit's player object
		
	public boolean isSneaking() {
		return player.isSneaking();
	}
		
	public boolean isSprinting() {
		return player.isSprinting();
	}
		
	public void playNote(Location location, Instrument instrument, Note note) {
		player.playNote(location, instrument, note);
	}
		
	public void playSound(Location location, Sound sound, float volume, float pitch) {
		player.playSound(location, sound, volume, pitch);
	}
	
	public void playSound(Sound sound, float volume, float pitch) {
		playSound(player.getLocation(), sound, volume, pitch);
	}
		
	public void updateInventory() {
		player.updateInventory();
	}
		
	public float getExp() {
		return player.getExp();
	}
		
	public void setExp(float exp) {
		player.setExp(exp);
	}
		
	public int getLevel() {
		return player.getLevel();
	}
		
	public void setLevel(int level) {
		player.setLevel(level);
	}
		
	public int getFoodLevel() {
		return player.getFoodLevel();
	}
		
	public void setFoodLevel(int foodLevel) {
		player.setFoodLevel(foodLevel);
	}
		
	public boolean isOnline() {
		return player.isOnline();
	}
		
	public boolean getAllowFlight() {
		return player.getAllowFlight();
	}
		
	public void setAllowFlight(boolean allowFlight) {
		player.setAllowFlight(allowFlight);
	}
		
	public void hidePlayer(GamePlayer gamePlayer) {
		player.hidePlayer(gamePlayer.getPlayer());
	}
		
	public void showPlayer(GamePlayer gamePlayer) {
		player.showPlayer(gamePlayer.getPlayer());
	}
		
	public boolean canSee(GamePlayer gamePlayer) {
		return player.canSee(gamePlayer.getPlayer());
	}
		
	public Location getLocation() {
		return player.getLocation();
	}
		
	public void setVelocity(Vector velocity) {
		player.setVelocity(velocity);
	}
		
	public Vector getVelocity() {
		return player.getVelocity();
	}
		
	public World getWorld() {
		return player.getWorld();
	}
		
	public boolean teleport(Location location) {
		return player.teleport(location);
	}
		
	public int getFireTicks() {
		return player.getFireTicks();
	}
		
	public int getMaxFireTicks() {
		return player.getMaxFireTicks();
	}
		
	public void setFireTicks(int fireTicks) {
		player.setFireTicks(fireTicks);
	}
		
	public void sendMessage(String message) {
		player.sendMessage(message);
	}
		
	public boolean isFlying() {
		return player.isFlying();
	}
		
	public void setFlying(boolean flying) {
		player.setFlying(flying);
	}
		
	public Scoreboard getScoreboard() {
		return player.getScoreboard();
	}
		
	public void setScoreboard(Scoreboard scoreboard) {
		player.setScoreboard(scoreboard);
	}
		
	public String getName() {
		return player.getName();
	}
		
	public PlayerInventory getInventory() {
		return player.getInventory();
	}
	
	public void clearInventory() {
		getInventory().clear();
	}
		
	public Inventory getEnderChest() {
		return player.getEnderChest();
	}
		
	public InventoryView getInventoryView() {
		return player.getOpenInventory();
	}
		
	public InventoryView openInventory(Inventory inventory) {
		return player.openInventory(inventory);
	}
		
	public void openInventoryView(InventoryView view) {
		player.openInventory(view);
	}
		
	public void closeInventory() {
		player.closeInventory();
	}
		
	public GameMode getGameMode() {
		return player.getGameMode();
	}
		
	public void setGameMode(GameMode gameMode) {
		player.setGameMode(gameMode);
	}
		
	public Location getEyeLocation() {
		return player.getEyeLocation();
	}
		
	public Block getTargetBlock(int distance) {
		return player.getTargetBlock(IGNORED_TARGET_BLOCK_MATERIALS, distance);
	}
		
	public int getMaximumNoDamageTicks() {
		return player.getMaximumNoDamageTicks();
	}
		
	public void setMaximumNoDamageTicks(int maximumNoDamageTicks) {
		player.setMaximumNoDamageTicks(maximumNoDamageTicks);
	}
		
	public int getNoDamageTicks() {
		return player.getNoDamageTicks();
	}
		
	public void setNoDamageTicks(int noDamageTicks) {
		player.setNoDamageTicks(noDamageTicks);
	}
		
	public boolean addPotionEffect(PotionEffect effect) {
		return player.addPotionEffect(effect);
	}
		
	public boolean addPotionEffect(PotionEffect effect, boolean b) {
		return player.addPotionEffect(effect, b);
	}
		
	public boolean hasPotionEffect(PotionEffectType type) {
		return player.hasPotionEffect(type);
	}
	
	public boolean hasPotionEffect(PotionEffect effect) {
		return hasPotionEffect(effect.getType());
	}
		
	public void removePotionEffect(PotionEffectType type) {
		player.removePotionEffect(type);
	}
	
	public void removePotionEffect(PotionEffect effect) {
		removePotionEffect(effect.getType());
	}
		
	public Collection<PotionEffect> getActivePotionEffects() {
		return player.getActivePotionEffects();
	}
	
	public void clearActivePotionEffects() {
		getActivePotionEffects().iterator().forEachRemaining(this::removePotionEffect);
	}
		
	public boolean hasLineOfSight(Entity entity) {
		return player.hasLineOfSight(entity);
	}
		
	public void damage(double damage) {
		player.damage(damage);
	}
	
	public void damage(double damage, Entity damager) {
		player.damage(damage, damager);
	}
		
	public double getHealth() {
		return player.getHealth();
	}
		
	public void setHealth(double health) {
		player.setHealth(health);
	}
	
	public void setHealthToMax() {
		setHealth(getMaxHealth());
	}
		
	public double getMaxHealth() {
		return player.getMaxHealth();
	}
		
	public void setMaxHealth(double maxHealth) {
		player.setMaxHealth(maxHealth);
	}
		
	public void resetMaxHealth() {
		player.resetMaxHealth();
	}
		
	public boolean hasPermission(String permission) {
		return player.hasPermission(permission);
	}
		
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectileClass) {
		return player.launchProjectile(projectileClass);
	}
		
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectileClass, Vector velocity) {
		return player.launchProjectile(projectileClass, velocity);
	}
	
}
