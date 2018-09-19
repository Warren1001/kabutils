package com.kabryxis.kabutils.spigot.event;

import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Listeners {
	
	private static final Set<RegisteredListener> globalListeners = new HashSet<>();
	private static final EventExecutor globalExecutor = (listener, event) -> ((GlobalListener)listener).onEvent(event);
	
	private static final List<HandlerList> globalHandlerList;
	
	static {
		List<HandlerList> globalHandlerListLocal = null;
		try {
			Field field = HandlerList.class.getDeclaredField("allLists");
			field.setAccessible(true);
			globalHandlerListLocal = new ArrayList<HandlerList>((List<HandlerList>)field.get(null)) {
				
				@Override
				public boolean add(HandlerList list) {
					boolean b = super.add(list);
					if(b) list.registerAll(globalListeners);
					return b;
				}
				
			};
			field.set(null, globalHandlerListLocal);
		} catch(NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		globalHandlerList = globalHandlerListLocal;
	}
	
	public static void registerListener(GlobalListener listener, Plugin plugin) {
		registerListener(listener, plugin, EventPriority.LOWEST);
	}
	
	public static void registerListener(GlobalListener listener, Plugin plugin, EventPriority priority) {
		registerListener(listener, plugin, priority, globalExecutor);
	}
	
	public static void registerListener(GlobalListener listener, Plugin plugin, EventExecutor executor) {
		registerListener(listener, plugin, EventPriority.LOWEST, executor);
	}
	
	public static void registerListener(GlobalListener listener, Plugin plugin, EventPriority priority, EventExecutor executor) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		RegisteredListener registeredListener = new RegisteredListener(listener, executor, priority, plugin, false);
		globalListeners.add(registeredListener);
		globalHandlerList.forEach(hl -> hl.register(registeredListener));
	}
	
}
