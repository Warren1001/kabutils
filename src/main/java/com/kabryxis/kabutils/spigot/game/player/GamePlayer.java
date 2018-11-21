package com.kabryxis.kabutils.spigot.game.player;

import com.google.common.collect.Sets;
import com.kabryxis.kabutils.data.Arrays;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.*;

public class GamePlayer implements Player {
	
	public static final Set<Material> IGNORED_TARGET_BLOCK_MATERIALS = Sets.newHashSet(Material.AIR, Material.CARPET);
	
	protected final UUID uuid;
	
	public GamePlayer(UUID uuid) {
		this.uuid = uuid;
	}
	
	protected Player player;
	
	public void updatePlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public UUID getUniqueId() {
		return uuid;
	}
	
	public void reset(ResetFlag... flags) {
		Arrays.forEach(flags, flag -> flag.reset(this));
	}
	
	public void resetAll() {
		reset(ResetFlag.values());
	}
	
	public void reset(Location spawn, ResetFlag... flags) {
		reset(flags);
		teleport(spawn);
	}
	
	public void resetAll(Location spawn) {
		reset(spawn, ResetFlag.values());
	}
	
	public void hide() {
		getServer().getOnlinePlayers().forEach(p -> p.hidePlayer(player));
	}
	
	public void sendMessage(String message, Object... objs) {
		sendMessage(String.format(message, objs));
	}
	
	public Block getTargetBlock(int distance) {
		return getTargetBlock(IGNORED_TARGET_BLOCK_MATERIALS, distance);
	}
	
	public boolean hasPotionEffect(PotionEffect effect) {
		return hasPotionEffect(effect.getType());
	}
	
	public void removePotionEffect(PotionEffect effect) {
		removePotionEffect(effect.getType());
	}
	
	public void clearActivePotionEffects() {
		getActivePotionEffects().iterator().forEachRemaining(this::removePotionEffect);
	}
	
	public void playSound(Sound sound, float volume, float pitch) {
		playSound(player.getLocation(), sound, volume, pitch);
	}
	
	public void setHealthToMax() {
		setHealth(getMaxHealth());
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof GamePlayer && ((GamePlayer)obj).uuid.equals(uuid);
	}
	
	@Override
	public String toString() {
		return String.format("GamePlayer{uuid=%s,name=%s}", uuid, getName());
	}
	
	/*
		bukkit methods with no overrides
	*/
	
	@Override
	public long getFirstPlayed() {
		return player.getFirstPlayed();
	}
	
	@Override
	public long getLastPlayed() {
		return player.getLastPlayed();
	}
	
	@Override
	public boolean hasPlayedBefore() {
		return player.hasPlayedBefore();
	}
	
	@Override
	public int getTicksLived() {
		return player.getTicksLived();
	}
	
	@Override
	public void setTicksLived(int i) {
		player.setTicksLived(i);
	}
	
	@Override
	public void playEffect(EntityEffect effect) {
		player.playEffect(effect);
	}
	
	@Override
	public EntityType getType() {
		return player.getType();
	}
	
	@Override
	public boolean isInsideVehicle() {
		return player.isInsideVehicle();
	}
	
	@Override
	public boolean leaveVehicle() {
		return player.leaveVehicle();
	}
	
	@Override
	public Entity getVehicle() {
		return player.getVehicle();
	}
	
	@Override
	public void setCustomName(String s) {
		player.setCustomName(s);
	}
	
	@Override
	public String getCustomName() {
		return player.getCustomName();
	}
	
	@Override
	public void setCustomNameVisible(boolean b) {
		player.setCustomNameVisible(b);
	}
	
	@Override
	public boolean isCustomNameVisible() {
		return player.isCustomNameVisible();
	}
	
	@Override
	public boolean isBanned() {
		return player.isBanned();
	}
	
	@Deprecated
	@Override
	public void setBanned(boolean b) {
		player.setBanned(b);
	}
	
	@Override
	public boolean isWhitelisted() {
		return player.isWhitelisted();
	}
	
	@Override
	public void setWhitelisted(boolean b) {
		player.setWhitelisted(b);
	}
	
	@Override
	public String getDisplayName() {
		return player.getDisplayName();
	}
	
	@Override
	public void setDisplayName(String s) {
		player.setDisplayName(s);
	}
	
	@Override
	public String getPlayerListName() {
		return player.getPlayerListName();
	}
	
	@Override
	public void setPlayerListName(String s) {
		player.setPlayerListName(s);
	}
	
	@Override
	public void setCompassTarget(Location location) {
		player.setCompassTarget(location);
	}
	
	@Override
	public Location getCompassTarget() {
		return player.getCompassTarget();
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return player.getAddress();
	}
	
	@Override
	public boolean isConversing() {
		return player.isConversing();
	}
	
	@Override
	public void acceptConversationInput(String s) {
		player.acceptConversationInput(s);
	}
	
	@Override
	public boolean beginConversation(Conversation conversation) {
		return player.beginConversation(conversation);
	}
	
	@Override
	public void abandonConversation(Conversation conversation) {
		player.abandonConversation(conversation);
	}
	
	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent event) {
		player.abandonConversation(conversation, event);
	}
	
	@Override
	public void sendRawMessage(String s) {
		player.sendRawMessage(s);
	}
	
	@Override
	public void kickPlayer(String s) {
		player.kickPlayer(s);
	}
	
	@Override
	public void chat(String s) {
		player.chat(s);
	}
	
	@Override
	public boolean performCommand(String s) {
		return player.performCommand(s);
	}
	
	@Override
	public boolean isSneaking() {
		return player.isSneaking();
	}
	
	@Override
	public void setSneaking(boolean b) {
		player.setSneaking(b);
	}
	
	@Override
	public boolean isSprinting() {
		return player.isSprinting();
	}
	
	@Override
	public void setSprinting(boolean b) {
		player.setSneaking(b);
	}
	
	@Override
	public void saveData() {
		player.saveData();
	}
	
	@Override
	public void loadData() {
		player.loadData();
	}
	
	@Override
	public void setSleepingIgnored(boolean b) {
		player.setSleepingIgnored(b);
	}
	
	@Override
	public boolean isSleepingIgnored() {
		return player.isSleepingIgnored();
	}
	
	@Deprecated
	@Override
	public void playNote(Location location, byte b, byte b1) {
		player.playNote(location, b, b1);
	}
	
	@Override
	public void playNote(Location location, Instrument instrument, Note note) {
		player.playNote(location, instrument, note);
	}
	
	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch) {
		player.playSound(location, sound, volume, pitch);
	}
	
	@Deprecated
	@Override
	public void playSound(Location location, String s, float v, float v1) {
		player.playSound(location, s, v, v1);
	}
	
	@Deprecated
	@Override
	public void playEffect(Location location, Effect effect, int i) {
		player.playEffect(location, effect, i);
	}
	
	@Override
	public <T> void playEffect(Location location, Effect effect, T t) {
		player.playEffect(location, effect, t);
	}
	
	@Deprecated
	@Override
	public void sendBlockChange(Location location, Material material, byte b) {
		player.sendBlockChange(location, material, b);
	}
	
	@Deprecated
	@Override
	public boolean sendChunkChange(Location location, int i, int i1, int i2, byte[] bytes) {
		return player.sendChunkChange(location, i, i1, i2, bytes);
	}
	
	@Deprecated
	@Override
	public void sendBlockChange(Location location, int i, byte b) {
		player.sendBlockChange(location, i, b);
	}
	
	@Override
	public void sendSignChange(Location location, String[] strings) {
		player.sendSignChange(location, strings);
	}
	
	@Override
	public void sendMap(MapView view) {
		player.sendMap(view);
	}
	
	@Override
	public void updateInventory() {
		player.updateInventory();
	}
	
	@Override
	public void awardAchievement(Achievement achievement) {
		player.awardAchievement(achievement);
	}
	
	@Override
	public void removeAchievement(Achievement achievement) {
		player.removeAchievement(achievement);
	}
	
	@Override
	public boolean hasAchievement(Achievement achievement) {
		return player.hasAchievement(achievement);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic) {
		player.incrementStatistic(statistic);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic) {
		player.decrementStatistic(statistic);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, int i) {
		player.incrementStatistic(statistic, i);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic, int i) {
		player.decrementStatistic(statistic, i);
	}
	
	@Override
	public void setStatistic(Statistic statistic, int i) {
		player.setStatistic(statistic, i);
	}
	
	@Override
	public int getStatistic(Statistic statistic) {
		return player.getStatistic(statistic);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, Material material) {
		player.incrementStatistic(statistic, material);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic, Material material) {
		player.decrementStatistic(statistic, material);
	}
	
	@Override
	public int getStatistic(Statistic statistic, Material material) {
		return player.getStatistic(statistic, material);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, Material material, int i) {
		player.incrementStatistic(statistic, material, i);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic, Material material, int i) {
		player.decrementStatistic(statistic, material, i);
	}
	
	@Override
	public void setStatistic(Statistic statistic, Material material, int i) {
		player.setStatistic(statistic, material, i);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, EntityType type) {
		player.incrementStatistic(statistic, type);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic, EntityType type) {
		player.decrementStatistic(statistic, type);
	}
	
	@Override
	public int getStatistic(Statistic statistic, EntityType type) {
		return player.getStatistic(statistic, type);
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, EntityType type, int i) {
		player.incrementStatistic(statistic, type, i);
	}
	
	@Override
	public void decrementStatistic(Statistic statistic, EntityType type, int i) {
		player.decrementStatistic(statistic, type, i);
	}
	
	@Override
	public void setStatistic(Statistic statistic, EntityType type, int i) {
		player.setStatistic(statistic, type, i);
	}
	
	@Override
	public void setPlayerTime(long l, boolean b) {
		player.setPlayerTime(l, b);
	}
	
	@Override
	public long getPlayerTime() {
		return player.getPlayerTime();
	}
	
	@Override
	public long getPlayerTimeOffset() {
		return player.getPlayerTimeOffset();
	}
	
	@Override
	public boolean isPlayerTimeRelative() {
		return player.isPlayerTimeRelative();
	}
	
	@Override
	public void resetPlayerTime() {
		player.resetPlayerTime();
	}
	
	@Override
	public void setPlayerWeather(WeatherType type) {
		player.setPlayerWeather(type);
	}
	
	@Override
	public WeatherType getPlayerWeather() {
		return player.getPlayerWeather();
	}
	
	@Override
	public void resetPlayerWeather() {
		player.resetPlayerWeather();
	}
	
	@Override
	public void giveExp(int i) {
		player.giveExp(i);
	}
	
	@Override
	public void giveExpLevels(int i) {
		player.giveExpLevels(i);
	}
	
	@Override
	public float getExp() {
		return player.getExp();
	}
	
	@Override
	public void setExp(float exp) {
		player.setExp(exp);
	}
	
	@Override
	public int getLevel() {
		return player.getLevel();
	}
	
	@Override
	public void setLevel(int level) {
		player.setLevel(level);
	}
	
	@Override
	public int getTotalExperience() {
		return player.getTotalExperience();
	}
	
	@Override
	public void setTotalExperience(int i) {
		player.setTotalExperience(i);
	}
	
	@Override
	public float getExhaustion() {
		return player.getExhaustion();
	}
	
	@Override
	public void setExhaustion(float v) {
		player.setExhaustion(v);
	}
	
	@Override
	public float getSaturation() {
		return player.getSaturation();
	}
	
	@Override
	public void setSaturation(float v) {
		player.setSaturation(v);
	}
	
	@Override
	public int getFoodLevel() {
		return player.getFoodLevel();
	}
	
	@Override
	public void setFoodLevel(int foodLevel) {
		player.setFoodLevel(foodLevel);
	}
	
	@Override
	public Location getBedSpawnLocation() {
		return player.getBedSpawnLocation();
	}
	
	@Override
	public void setBedSpawnLocation(Location location) {
		player.setBedSpawnLocation(location);
	}
	
	@Override
	public void setBedSpawnLocation(Location location, boolean b) {
		player.setBedSpawnLocation(location, b);
	}
	
	@Override
	public boolean isOnline() {
		return player.isOnline();
	}
	
	@Override
	public boolean getAllowFlight() {
		return player.getAllowFlight();
	}
	
	@Override
	public void setAllowFlight(boolean allowFlight) {
		player.setAllowFlight(allowFlight);
	}
	
	@Override
	public void hidePlayer(Player player) {
		player.hidePlayer(player);
	}
	
	@Override
	public void showPlayer(Player player) {
		player.showPlayer(player);
	}
	
	@Override
	public boolean canSee(Player player) {
		return player.canSee(player);
	}
	
	@Deprecated
	@Override
	public boolean isOnGround() {
		return player.isOnGround();
	}
	
	@Override
	public Location getLocation() {
		return player.getLocation();
	}
	
	@Override
	public Location getLocation(Location location) {
		return player.getLocation(location);
	}
	
	@Override
	public void setVelocity(Vector velocity) {
		player.setVelocity(velocity);
	}
	
	@Override
	public Vector getVelocity() {
		return player.getVelocity();
	}
	
	@Override
	public World getWorld() {
		return player.getWorld();
	}
	
	@Override
	public boolean teleport(Location location) {
		return player.teleport(location);
	}
	
	@Override
	public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
		return player.teleport(location, cause);
	}
	
	@Override
	public boolean teleport(Entity entity) {
		return player.teleport(entity);
	}
	
	@Override
	public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause cause) {
		return player.teleport(entity, cause);
	}
	
	@Override
	public List<Entity> getNearbyEntities(double v, double v1, double v2) {
		return player.getNearbyEntities(v, v1, v2);
	}
	
	@Override
	public int getEntityId() {
		return player.getEntityId();
	}
	
	@Override
	public int getFireTicks() {
		return player.getFireTicks();
	}
	
	@Override
	public int getMaxFireTicks() {
		return player.getMaxFireTicks();
	}
	
	@Override
	public void setFireTicks(int fireTicks) {
		player.setFireTicks(fireTicks);
	}
	
	@Override
	public void remove() {
		player.remove();
	}
	
	@Override
	public boolean isDead() {
		return player.isDead();
	}
	
	@Override
	public boolean isValid() {
		return player.isValid();
	}
	
	@Override
	public Server getServer() {
		return player.getServer();
	}
	
	@Override
	public Entity getPassenger() {
		return player.getPassenger();
	}
	
	@Override
	public boolean setPassenger(Entity entity) {
		return player.setPassenger(entity);
	}
	
	@Override
	public boolean isEmpty() {
		return player.isEmpty();
	}
	
	@Override
	public boolean eject() {
		return player.eject();
	}
	
	@Override
	public float getFallDistance() {
		return player.getFallDistance();
	}
	
	@Override
	public void setFallDistance(float v) {
		player.setFallDistance(v);
	}
	
	@Override
	public void setLastDamageCause(EntityDamageEvent event) {
		player.setLastDamageCause(event);
	}
	
	@Override
	public EntityDamageEvent getLastDamageCause() {
		return player.getLastDamageCause();
	}
	
	@Override
	public void sendMessage(String message) {
		player.sendMessage(message);
	}
	
	@Override
	public void sendMessage(String[] strings) {
		player.sendMessage(strings);
	}
	
	@Override
	public boolean isFlying() {
		return player.isFlying();
	}
	
	@Override
	public void setFlying(boolean flying) {
		player.setFlying(flying);
	}
	
	@Override
	public void setFlySpeed(float v) {
		player.setFlySpeed(v);
	}
	
	@Override
	public void setWalkSpeed(float v)  {
		player.setWalkSpeed(v);
	}
	
	@Override
	public float getFlySpeed() {
		return player.getFlySpeed();
	}
	
	@Override
	public float getWalkSpeed() {
		return player.getWalkSpeed();
	}
	
	@Deprecated
	@Override
	public void setTexturePack(String s) {
		player.setTexturePack(s);
	}
	
	@Override
	public void setResourcePack(String s) {
		player.setResourcePack(s);
	}
	
	@Override
	public Scoreboard getScoreboard() {
		return player.getScoreboard();
	}
	
	@Override
	public void setScoreboard(Scoreboard scoreboard) {
		player.setScoreboard(scoreboard);
	}
	
	@Override
	public boolean isHealthScaled() {
		return player.isHealthScaled();
	}
	
	@Override
	public void setHealthScaled(boolean b) {
		player.setHealthScaled(b);
	}
	
	@Override
	public void setHealthScale(double v)  {
		player.setHealthScale(v);
	}
	
	@Override
	public double getHealthScale() {
		return player.getHealthScale();
	}
	
	@Override
	public Spigot spigot() {
		return player.spigot();
	}
	
	@Override
	public String getName() {
		return player.getName();
	}
	
	@Override
	public PlayerInventory getInventory() {
		return player.getInventory();
	}
	
	@Override
	public Inventory getEnderChest() {
		return player.getEnderChest();
	}
	
	@Override
	public boolean setWindowProperty(InventoryView.Property property, int i) {
		return player.setWindowProperty(property, i);
	}
	
	@Override
	public InventoryView getOpenInventory() {
		return player.getOpenInventory();
	}
	
	@Override
	public InventoryView openInventory(Inventory inventory) {
		return player.openInventory(inventory);
	}
	
	@Override
	public InventoryView openWorkbench(Location location, boolean b) {
		return player.openWorkbench(location, b);
	}
	
	@Override
	public InventoryView openEnchanting(Location location, boolean b) {
		return player.openEnchanting(location, b);
	}
	
	@Override
	public void openInventory(InventoryView view) {
		player.openInventory(view);
	}
	
	@Override
	public void closeInventory() {
		player.closeInventory();
	}
	
	@Override
	public ItemStack getItemInHand() {
		return player.getItemInHand();
	}
	
	@Override
	public void setItemInHand(ItemStack stack) {
		player.setItemInHand(stack);
	}
	
	@Override
	public ItemStack getItemOnCursor() {
		return player.getItemOnCursor();
	}
	
	@Override
	public void setItemOnCursor(ItemStack stack) {
		player.setItemOnCursor(stack);
	}
	
	@Override
	public boolean isSleeping() {
		return player.isSleeping();
	}
	
	@Override
	public int getSleepTicks() {
		return player.getSleepTicks();
	}
	
	@Override
	public GameMode getGameMode() {
		return player.getGameMode();
	}
	
	@Override
	public void setGameMode(GameMode gameMode) {
		player.setGameMode(gameMode);
	}
	
	@Override
	public boolean isBlocking() {
		return player.isBlocking();
	}
	
	@Override
	public int getExpToLevel() {
		return player.getExpToLevel();
	}
	
	@Override
	public double getEyeHeight() {
		return player.getEyeHeight();
	}
	
	@Override
	public double getEyeHeight(boolean b) {
		return player.getEyeHeight(b);
	}
	
	@Override
	public Location getEyeLocation() {
		return player.getEyeLocation();
	}
	
	@Deprecated
	@Override
	public List<Block> getLineOfSight(HashSet<Byte> set, int i) {
		return player.getLineOfSight(set, i);
	}
	
	@Override
	public List<Block> getLineOfSight(Set<Material> set, int i) {
		return player.getLineOfSight(set, i);
	}
	
	@Deprecated
	@Override
	public Block getTargetBlock(HashSet<Byte> set, int i) {
		return player.getTargetBlock(set, i);
	}
	
	@Override
	public Block getTargetBlock(Set<Material> set, int i) {
		return player.getTargetBlock(set, i);
	}
	
	@Deprecated
	@Override
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> set, int i) {
		return player.getLastTwoTargetBlocks(set, i);
	}
	
	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
		return player.getLastTwoTargetBlocks(set, i);
	}
	
	@Deprecated
	@Override
	public Egg throwEgg() {
		return player.throwEgg();
	}
	
	@Deprecated
	@Override
	public Snowball throwSnowball() {
		return player.throwSnowball();
	}
	
	@Deprecated
	@Override
	public Arrow shootArrow() {
		return player.shootArrow();
	}
	
	@Override
	public int getRemainingAir() {
		return player.getRemainingAir();
	}
	
	@Override
	public void setRemainingAir(int i) {
		player.setRemainingAir(i);
	}
	
	@Override
	public int getMaximumAir() {
		return player.getMaximumAir();
	}
	
	@Override
	public void setMaximumAir(int i) {
		player.setMaximumAir(i);
	}
	
	@Override
	public int getMaximumNoDamageTicks() {
		return player.getMaximumNoDamageTicks();
	}
	
	@Override
	public void setMaximumNoDamageTicks(int maximumNoDamageTicks) {
		player.setMaximumNoDamageTicks(maximumNoDamageTicks);
	}
	
	@Override
	public double getLastDamage() {
		return player.getLastDamage();
	}
	
	@Override
	public void setLastDamage(double v) {
		player.setLastDamage(v);
	}
	
	@Override
	public int getNoDamageTicks() {
		return player.getNoDamageTicks();
	}
	
	@Override
	public void setNoDamageTicks(int noDamageTicks) {
		player.setNoDamageTicks(noDamageTicks);
	}
	
	@Override
	public Player getKiller() {
		return player.getKiller();
	}
	
	@Override
	public boolean addPotionEffect(PotionEffect effect) {
		return player.addPotionEffect(effect);
	}
	
	@Override
	public boolean addPotionEffect(PotionEffect effect, boolean b) {
		return player.addPotionEffect(effect, b);
	}
	
	@Override
	public boolean addPotionEffects(Collection<PotionEffect> collection) {
		return player.addPotionEffects(collection);
	}
	
	@Override
	public boolean hasPotionEffect(PotionEffectType type) {
		return player.hasPotionEffect(type);
	}
	
	@Override
	public void removePotionEffect(PotionEffectType type) {
		player.removePotionEffect(type);
	}
	
	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return player.getActivePotionEffects();
	}
	
	@Override
	public boolean hasLineOfSight(Entity entity) {
		return player.hasLineOfSight(entity);
	}
	
	@Override
	public boolean getRemoveWhenFarAway() {
		return player.getRemoveWhenFarAway();
	}
	
	@Override
	public void setRemoveWhenFarAway(boolean b) {
		player.setRemoveWhenFarAway(b);
	}
	
	@Override
	public EntityEquipment getEquipment() {
		return player.getEquipment();
	}
	
	@Override
	public void setCanPickupItems(boolean b) {
		player.setCanPickupItems(b);
	}
	
	@Override
	public boolean getCanPickupItems() {
		return player.getCanPickupItems();
	}
	
	@Override
	public boolean isLeashed() {
		return player.isLeashed();
	}
	
	@Override
	public Entity getLeashHolder() {
		return player.getLeashHolder();
	}
	
	@Override
	public boolean setLeashHolder(Entity entity) {
		return player.setLeashHolder(entity);
	}
	
	@Override
	public void damage(double damage) {
		player.damage(damage);
	}
	
	@Override
	public void damage(double damage, Entity damager) {
		player.damage(damage, damager);
	}
	
	@Override
	public double getHealth() {
		return player.getHealth();
	}
	
	@Override
	public void setHealth(double health) {
		player.setHealth(health);
	}
	
	@Override
	public double getMaxHealth() {
		return player.getMaxHealth();
	}
	
	@Override
	public void setMaxHealth(double maxHealth) {
		player.setMaxHealth(maxHealth);
	}
	
	@Override
	public void resetMaxHealth() {
		player.resetMaxHealth();
	}
	
	@Override
	public boolean isPermissionSet(String s) {
		return player.isPermissionSet(s);
	}
	
	@Override
	public boolean isPermissionSet(Permission permission) {
		return player.isPermissionSet(permission);
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return player.hasPermission(permission);
	}
	
	@Override
	public boolean hasPermission(Permission permission) {
		return player.hasPermission(permission);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
		return player.addAttachment(plugin, s, b);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return player.addAttachment(plugin);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
		return player.addAttachment(plugin, s, b, i);
	}
	
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int i) {
		return player.addAttachment(plugin, i);
	}
	
	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		player.removeAttachment(attachment);
	}
	
	@Override
	public void recalculatePermissions() {
		player.recalculatePermissions();
	}
	
	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}
	
	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectileClass) {
		return player.launchProjectile(projectileClass);
	}
	
	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectileClass, Vector velocity) {
		return player.launchProjectile(projectileClass, velocity);
	}
	
	@Override
	public Map<String, Object> serialize() {
		return player.serialize();
	}
	
	@Override
	public void setMetadata(String s, MetadataValue value) {
		player.setMetadata(s, value);
	}
	
	@Override
	public List<MetadataValue> getMetadata(String s) {
		return player.getMetadata(s);
	}
	
	@Override
	public boolean hasMetadata(String s) {
		return player.hasMetadata(s);
	}
	
	@Override
	public void removeMetadata(String s, Plugin plugin) {
		player.removeMetadata(s, plugin);
	}
	
	@Override
	public boolean isOp() {
		return player.isOp();
	}
	
	@Override
	public void setOp(boolean b) {
		player.setOp(b);
	}
	
	@Override
	public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {
		player.sendPluginMessage(plugin, s, bytes);
	}
	
	@Override
	public Set<String> getListeningPluginChannels() {
		return player.getListeningPluginChannels();
	}
	
}
