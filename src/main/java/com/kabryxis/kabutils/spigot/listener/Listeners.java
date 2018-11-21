package com.kabryxis.kabutils.spigot.listener;

import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import javax.annotation.Nullable;
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
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin) {
		return registerListener(listener, plugin, EventPriority.NORMAL);
	}
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin, EventPriority priority) {
		return registerListener(listener, plugin, priority, globalExecutor);
	}
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin, EventExecutor executor) {
		return registerListener(listener, plugin, EventPriority.NORMAL, executor);
	}
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin, EventPriority priority, @Nullable EventExecutor executor) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		if(listener instanceof GlobalListener) {
			RegisteredListener registeredListener = new RegisteredListener(listener, executor, priority, plugin, false);
			globalListeners.add(registeredListener);
			globalHandlerList.forEach(hl -> hl.register(registeredListener));
		}
		return listener;
	}
	
}
