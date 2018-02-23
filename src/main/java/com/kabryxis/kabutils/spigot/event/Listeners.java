package com.kabryxis.kabutils.spigot.event;

import com.google.common.collect.Sets;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Listeners {
	
	@SuppressWarnings("unchecked")
	private static final Set<Class<? extends Event>> CLASSES = Sets.newHashSet(AsyncPlayerPreLoginEvent.class, BlockBurnEvent.class,
			BlockCanBuildEvent.class, BlockDamageEvent.class, BlockDispenseEvent.class, BlockExpEvent.class, BlockFadeEvent.class,
			BlockFromToEvent.class, BlockGrowEvent.class, BlockIgniteEvent.class, BlockPhysicsEvent.class, BlockPistonExtendEvent.class,
			BlockPistonRetractEvent.class, BlockPlaceEvent.class, BlockRedstoneEvent.class, BrewEvent.class, FurnaceBurnEvent.class,
			FurnaceSmeltEvent.class, LeavesDecayEvent.class, NotePlayEvent.class, SignChangeEvent.class, CreeperPowerEvent.class,
			EntityChangeBlockEvent.class, EntityCombustEvent.class, EntityCreatePortalEvent.class, EntityDamageEvent.class, EntityDeathEvent.class,
			EntityDismountEvent.class, EntityExplodeEvent.class, EntityInteractEvent.class, EntityMountEvent.class, EntityPortalEnterEvent.class,
			EntityRegainHealthEvent.class, EntityShootBowEvent.class, EntitySpawnEvent.class, EntityTameEvent.class, EntityTargetEvent.class,
			EntityTeleportEvent.class, EntityUnleashEvent.class, ExplosionPrimeEvent.class, FoodLevelChangeEvent.class, HorseJumpEvent.class,
			ItemDespawnEvent.class, PigZapEvent.class, ProjectileHitEvent.class, ProjectileLaunchEvent.class, SheepDyeWoolEvent.class,
			SheepRegrowWoolEvent.class, SlimeSplitEvent.class, HangingBreakEvent.class, HangingPlaceEvent.class, EnchantItemEvent.class,
			InventoryCloseEvent.class, InventoryClickEvent.class, InventoryDragEvent.class, InventoryOpenEvent.class, PrepareItemCraftEvent.class,
			PrepareItemEnchantEvent.class, InventoryMoveItemEvent.class, InventoryPickupItemEvent.class, AsyncPlayerChatEvent.class,
			PlayerAchievementAwardedEvent.class, PlayerAnimationEvent.class, PlayerBedEnterEvent.class, PlayerBedLeaveEvent.class,
			PlayerBucketEmptyEvent.class, PlayerBucketFillEvent.class, PlayerChangedWorldEvent.class, PlayerChannelEvent.class,
			PlayerChatTabCompleteEvent.class, PlayerCommandPreprocessEvent.class, PlayerDropItemEvent.class, PlayerEditBookEvent.class,
			PlayerEggThrowEvent.class, PlayerExpChangeEvent.class, PlayerFishEvent.class, PlayerGameModeChangeEvent.class,
			PlayerInteractEntityEvent.class, PlayerInteractEvent.class, PlayerItemBreakEvent.class, PlayerItemConsumeEvent.class,
			PlayerItemDamageEvent.class, PlayerItemHeldEvent.class, PlayerJoinEvent.class, PlayerKickEvent.class, PlayerLevelChangeEvent.class,
			PlayerLoginEvent.class, PlayerMoveEvent.class, PlayerPickupItemEvent.class, PlayerQuitEvent.class, PlayerRespawnEvent.class,
			PlayerShearEntityEvent.class, PlayerSpawnLocationEvent.class, PlayerStatisticIncrementEvent.class, PlayerToggleFlightEvent.class,
			PlayerToggleSneakEvent.class, PlayerToggleSprintEvent.class, PlayerVelocityEvent.class, PlayerLeashEntityEvent.class,
			MapInitializeEvent.class, PluginDisableEvent.class, PluginEnableEvent.class, ServerCommandEvent.class, ServerListPingEvent.class,
			ServiceRegisterEvent.class, ServiceUnregisterEvent.class, VehicleBlockCollisionEvent.class, VehicleEntityCollisionEvent.class,
			VehicleCreateEvent.class, VehicleDamageEvent.class, VehicleDestroyEvent.class, VehicleEnterEvent.class, VehicleExitEvent.class,
			VehicleMoveEvent.class, VehicleUpdateEvent.class, LightningStrikeEvent.class, ThunderChangeEvent.class, WeatherChangeEvent.class,
			ChunkLoadEvent.class, ChunkPopulateEvent.class, ChunkUnloadEvent.class, PortalCreateEvent.class, SpawnChangeEvent.class,
			StructureGrowEvent.class, WorldInitEvent.class, WorldLoadEvent.class, WorldSaveEvent.class, WorldUnloadEvent.class);
	private static final Map<GlobalListener, RegisteredListener> globalListeners = new HashMap<>();
	private static final EventExecutor globalExecutor = (listener, event) -> {
		if(listener instanceof GlobalListener) ((GlobalListener)listener).onEvent(event);
	};
	
	public static void registerEvent(Class<? extends Event> clazz) {
		if(!CLASSES.contains(clazz)) {
			CLASSES.add(clazz);
			HandlerList hl = getHandlerList(clazz);
			globalListeners.values().forEach(hl::register);
		}
	}
	
	public static void registerListener(GlobalListener listener) {
		if(!globalListeners.containsKey(listener)) {
			RegisteredListener registeredListener = new RegisteredListener(listener, globalExecutor, EventPriority.LOWEST, listener.getPlugin(),
					false);
			globalListeners.put(listener, registeredListener);
			CLASSES.forEach(clazz -> {
				try {
					getHandlerList(clazz).register(registeredListener);
				}
				catch(IllegalStateException e) {
					System.out.println(clazz.getSimpleName() + " is already registered.");
				}
			});
		}
	}
	
	private static HandlerList getHandlerList(Class<?> clazz) {
		Method method;
		try {
			method = clazz.getMethod("getHandlerList");
		}
		catch(NoSuchMethodException e) {
			return null;
		}
		if(!method.isAccessible()) method.setAccessible(true);
		HandlerList hl;
		try {
			hl = (HandlerList)method.invoke(null);
		}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return hl;
	}
	
}
