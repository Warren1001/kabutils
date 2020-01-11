package com.kabryxis.kabutils.spigot.game.player;

import com.kabryxis.kabutils.data.Arrays;
import com.kabryxis.kabutils.spigot.plugin.Plugins;
import com.kabryxis.kabutils.spigot.plugin.protocollibrary.ProtocolLibGamePlayerAdapter;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
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
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePlayer implements Player {
	
	public static final Set<Material> IGNORED_TARGET_BLOCK_MATERIALS = Stream.of(Material.values()).filter(material -> material == Material.AIR || material.name().endsWith("CARPET")).collect(Collectors.toSet());
	
	protected final UUID uuid;
	protected final ProtocolLibGamePlayerAdapter protocolLibAdapter;
	
	public GamePlayer(UUID uuid) {
		this.uuid = uuid;
		protocolLibAdapter = Plugins.isInstalled(Plugins.ProtocolLib) ? new ProtocolLibGamePlayerAdapter(this) : null;
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
	
	public ProtocolLibGamePlayerAdapter getProtocolLibAdapter() {
		if(protocolLibAdapter == null) throw new UnsupportedOperationException("This method requires ProtocolLib to be installed to be used");
		return protocolLibAdapter;
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
	public void setGlowing(boolean b) {
		player.setGlowing(b);
	}
	
	@Override
	public boolean isGlowing() {
		return player.isGlowing();
	}
	
	@Override
	public void setInvulnerable(boolean b) {
		player.setInvulnerable(b);
	}
	
	@Override
	public boolean isInvulnerable() {
		return player.isInvulnerable();
	}
	
	@Override
	public boolean isSilent() {
		return player.isSilent();
	}
	
	@Override
	public void setSilent(boolean b) {
		player.setSilent(b);
	}
	
	@Override
	public boolean hasGravity() {
		return player.hasGravity();
	}
	
	@Override
	public void setGravity(boolean b) {
		player.setGravity(b);
	}
	
	@Override
	public int getPortalCooldown() {
		return player.getPortalCooldown();
	}
	
	@Override
	public void setPortalCooldown(int i) {
		player.setPortalCooldown(i);
	}
	
	@Override
	public Set<String> getScoreboardTags() {
		return player.getScoreboardTags();
	}
	
	@Override
	public boolean addScoreboardTag(String s) {
		return player.addScoreboardTag(s);
	}
	
	@Override
	public boolean removeScoreboardTag(String s) {
		return player.removeScoreboardTag(s);
	}
	
	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		return player.getPistonMoveReaction();
	}
	
	@Override
	public BlockFace getFacing() {
		return player.getFacing();
	}
	
	@Override
	public boolean isBanned() {
		return player.isBanned();
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
	public String getPlayerListHeader() {
		return player.getPlayerListHeader();
	}
	
	@Override
	public String getPlayerListFooter() {
		return player.getPlayerListFooter();
	}
	
	@Override
	public void setPlayerListHeader(String s) {
		player.setPlayerListHeader(s);
	}
	
	@Override
	public void setPlayerListFooter(String s) {
		player.setPlayerListFooter(s);
	}
	
	@Override
	public void setPlayerListHeaderFooter(String s, String s1) {
		player.setPlayerListHeaderFooter(s, s1);
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
	
	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float v, float v1) {
		player.playSound(location, sound, category, v, v1);
	}
	
	@Override
	public void playSound(Location location, String s, SoundCategory category, float v, float v1) {
		player.playSound(location, s, category, v, v1);
	}
	
	@Override
	public void stopSound(Sound sound) {
		player.stopSound(sound);
	}
	
	@Override
	public void stopSound(String s) {
		player.stopSound(s);
	}
	
	@Override
	public void stopSound(Sound sound, SoundCategory category) {
		player.stopSound(sound, category);
	}
	
	@Override
	public void stopSound(String s, SoundCategory category) {
		player.stopSound(s, category);
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
	
	@Override
	public void sendBlockChange(Location location, BlockData data) {
		player.sendBlockChange(location, data);
	}
	
	@Deprecated
	@Override
	public boolean sendChunkChange(Location location, int i, int i1, int i2, byte[] bytes) {
		return player.sendChunkChange(location, i, i1, i2, bytes);
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
	
	@Deprecated
	@Override
	public void awardAchievement(Achievement achievement) {
		player.awardAchievement(achievement);
	}
	
	@Deprecated
	@Override
	public void removeAchievement(Achievement achievement) {
		player.removeAchievement(achievement);
	}
	
	@Deprecated
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
	public boolean sleep(Location location, boolean b) {
		return player.sleep(location, b);
	}
	
	@Override
	public void wakeup(boolean b) {
		player.wakeup(b);
	}
	
	@Override
	public Location getBedLocation() {
		return player.getBedLocation();
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
	
	@Deprecated
	@Override
	public void hidePlayer(Player player) {
		this.player.hidePlayer(player);
	}
	
	@Override
	public void hidePlayer(Plugin plugin, Player player) {
		this.player.hidePlayer(plugin, player);
	}
	
	@Deprecated
	@Override
	public void showPlayer(Player player) {
		this.player.showPlayer(player);
	}
	
	@Override
	public void showPlayer(Plugin plugin, Player player) {
		this.player.showPlayer(plugin, player);
	}
	
	@Override
	public boolean canSee(Player player) {
		return player.canSee(player);
	}
	
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
	public double getHeight() {
		return player.getHeight();
	}
	
	@Override
	public double getWidth() {
		return player.getWidth();
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		return player.getBoundingBox();
	}
	
	@Override
	public World getWorld() {
		return player.getWorld();
	}
	
	@Deprecated
	@Override
	public void setRotation(float v, float v1) {
		player.setRotation(v, v1);
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
	
	@Deprecated
	@Override
	public boolean isPersistent() {
		return player.isPersistent();
	}
	
	@Deprecated
	@Override
	public void setPersistent(boolean b) {
		player.setPersistent(b);
	}
	
	@Deprecated
	@Override
	public Entity getPassenger() {
		return player.getPassenger();
	}
	
	@Deprecated
	@Override
	public boolean setPassenger(Entity entity) {
		return player.setPassenger(entity);
	}
	
	@Override
	public List<Entity> getPassengers() {
		return player.getPassengers();
	}
	
	@Override
	public boolean addPassenger(Entity entity) {
		return player.addPassenger(entity);
	}
	
	@Override
	public boolean removePassenger(Entity entity) {
		return player.removePassenger(entity);
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
	public void setResourcePack(String s, byte[] bytes) {
		player.setResourcePack(s, bytes);
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
	public Entity getSpectatorTarget() {
		return player.getSpectatorTarget();
	}
	
	@Override
	public void setSpectatorTarget(Entity entity) {
		player.setSpectatorTarget(entity);
	}
	
	@Deprecated
	@Override
	public void sendTitle(String title, String subtitle) {
		player.sendTitle(title, subtitle);
	}
	
	@Override
	public void sendTitle(String title, String subtitle, int i, int i1, int i2) {
		player.sendTitle(title, subtitle, i, i1, i2);
	}
	
	@Override
	public void resetTitle() {
		player.resetTitle();
	}
	
	@Override
	public void spawnParticle(Particle particle, Location location, int i) {
		player.spawnParticle(particle, location, i);
	}
	
	@Override
	public void spawnParticle(Particle particle, double v, double v1, double v2, int i) {
	player.spawnParticle(particle, v, v1, v2, i);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, Location location, int i, T t) {
		player.spawnParticle(particle, location, i, t);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, T t) {
		player.spawnParticle(particle, v, v1, v2, i, t);
	}
	
	@Override
	public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2) {
		player.spawnParticle(particle, location, i, v, v1, v2);
	}
	
	@Override
	public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
		player.spawnParticle(particle, v, v1, v2, i, v3, v4, v5);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, T t) {
		player.spawnParticle(particle, location, i, v, v1, v2, t);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, T t) {
		player.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, t);
	}
	
	@Override
	public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3) {
		player.spawnParticle(particle, location, i, v, v1, v2, v3);
	}
	
	@Override
	public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
		player.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3, T t) {
		player.spawnParticle(particle, location, i, v, v1, v2, v3, t);
	}
	
	@Override
	public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, T t) {
		player.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6, t);
	}
	
	@Override
	public AdvancementProgress getAdvancementProgress(Advancement advancement) {
		return player.getAdvancementProgress(advancement);
	}
	
	@Override
	public int getClientViewDistance() {
		return player.getClientViewDistance();
	}
	
	@Override
	public String getLocale() {
		return player.getLocale();
	}
	
	@Override
	public void updateCommands() {
		player.updateCommands();
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
	public MainHand getMainHand() {
		return player.getMainHand();
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
	public InventoryView openMerchant(Villager villager, boolean b) {
		return player.openMerchant(villager, b);
	}
	
	@Override
	public InventoryView openMerchant(Merchant merchant, boolean b) {
		return player.openMerchant(merchant, b);
	}
	
	@Override
	public void closeInventory() {
		player.closeInventory();
	}
	
	@Deprecated
	@Override
	public ItemStack getItemInHand() {
		return player.getItemInHand();
	}
	
	@Deprecated
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
	public boolean hasCooldown(Material material) {
		return player.hasCooldown(material);
	}
	
	@Override
	public int getCooldown(Material material) {
		return player.getCooldown(material);
	}
	
	@Override
	public void setCooldown(Material material, int i) {
		player.setCooldown(material, i);
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
	public boolean isHandRaised() {
		return player.isHandRaised();
	}
	
	@Override
	public int getExpToLevel() {
		return player.getExpToLevel();
	}
	
	@Override
	public boolean discoverRecipe(NamespacedKey key) {
		return player.discoverRecipe(key);
	}
	
	@Override
	public int discoverRecipes(Collection<NamespacedKey> collection) {
		return player.discoverRecipes(collection);
	}
	
	@Override
	public boolean undiscoverRecipe(NamespacedKey key) {
		return player.undiscoverRecipe(key);
	}
	
	@Override
	public int undiscoverRecipes(Collection<NamespacedKey> collection) {
		return player.undiscoverRecipes(collection);
	}
	
	@Deprecated
	@Override
	public Entity getShoulderEntityLeft() {
		return player.getShoulderEntityLeft();
	}
	
	@Deprecated
	@Override
	public void setShoulderEntityLeft(Entity entity) {
		player.setShoulderEntityLeft(entity);
	}
	
	@Deprecated
	@Override
	public Entity getShoulderEntityRight() {
		return player.getShoulderEntityRight();
	}
	
	@Deprecated
	@Override
	public void setShoulderEntityRight(Entity entity) {
		player.setShoulderEntityRight(entity);
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
	
	@Override
	public List<Block> getLineOfSight(Set<Material> set, int i) {
		return player.getLineOfSight(set, i);
	}
	
	@Override
	public Block getTargetBlock(Set<Material> set, int i) {
		return player.getTargetBlock(set, i);
	}
	
	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
		return player.getLastTwoTargetBlocks(set, i);
	}
	
	@Override
	public Block getTargetBlockExact(int i) {
		return player.getTargetBlockExact(i);
	}
	
	@Override
	public Block getTargetBlockExact(int i, FluidCollisionMode mode) {
		return player.getTargetBlockExact(i, mode);
	}
	
	@Override
	public RayTraceResult rayTraceBlocks(double v) {
		return player.rayTraceBlocks(v);
	}
	
	@Override
	public RayTraceResult rayTraceBlocks(double v, FluidCollisionMode mode) {
		return player.rayTraceBlocks(v, mode);
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
	public PotionEffect getPotionEffect(PotionEffectType type) {
		return player.getPotionEffect(type);
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
		return player.hasLineOfSight(entity instanceof Player ? ((Player)entity).getPlayer() : entity);
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
	public boolean isGliding() {
		return player.isGliding();
	}
	
	@Override
	public void setGliding(boolean b) {
		player.setGliding(b);
	}
	
	@Override
	public boolean isSwimming() {
		return player.isSwimming();
	}
	
	@Override
	public void setSwimming(boolean b) {
		player.setSwimming(b);
	}
	
	@Override
	public boolean isRiptiding() {
		return player.isRiptiding();
	}
	
	@Override
	public void setAI(boolean b) {
		player.setAI(b);
	}
	
	@Override
	public boolean hasAI() {
		return player.hasAI();
	}
	
	@Override
	public void setCollidable(boolean b) {
		player.setCollidable(b);
	}
	
	@Override
	public boolean isCollidable() {
		return player.isCollidable();
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
	
	@Deprecated
	@Override
	public double getMaxHealth() {
		return player.getMaxHealth();
	}
	
	@Deprecated
	@Override
	public void setMaxHealth(double maxHealth) {
		player.setMaxHealth(maxHealth);
	}
	
	@Deprecated
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
		return player.getEffectivePermissions();
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
	
	@Override
	public AttributeInstance getAttribute(Attribute attribute) {
		return player.getAttribute(attribute);
	}
	
}
