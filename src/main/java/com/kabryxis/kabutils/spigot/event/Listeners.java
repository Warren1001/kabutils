package com.kabryxis.kabutils.spigot.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import com.google.common.collect.Sets;

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
	private static final EventExecutor globalExecutor = new EventExecutor() {
		
		@Override
		public void execute(Listener listener, Event event) throws EventException {
			if(listener instanceof GlobalListener) ((GlobalListener)listener).onEvent(event);
		}
		
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
