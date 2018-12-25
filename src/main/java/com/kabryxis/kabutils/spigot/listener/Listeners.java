package com.kabryxis.kabutils.spigot.listener;

import com.kabryxis.kabutils.NoArgPredicate;
import com.kabryxis.kabutils.utility.ReflectionHelper;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class Listeners {
	
	public static final EventExecutor DEFAULT_GLOBAL_EXECUTOR = (listener, event) -> ((GlobalListener)listener).onEvent(event);
	
	private static final Set<RegisteredListener> GLOBAL_LISTENERS = new HashSet<>();
	private static final Map<Class<? extends Event>, HandlerList> HANDLER_LIST_CACHE = new HashMap<>();
	
	private static final List<HandlerList> ALL_HANDLER_LISTS;
	
	static {
		List<HandlerList> globalHandlerListLocal = null;
		try {
			Field field = HandlerList.class.getDeclaredField("allLists");
			field.setAccessible(true);
			globalHandlerListLocal = new ArrayList<HandlerList>((List<HandlerList>)field.get(null)) {
				
				@Override
				public boolean add(HandlerList list) {
					boolean b = super.add(list);
					if(b) list.registerAll(GLOBAL_LISTENERS);
					return b;
				}
				
			};
			field.set(null, globalHandlerListLocal);
		} catch(NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		ALL_HANDLER_LISTS = globalHandlerListLocal;
	}
	
	public static <T extends GlobalListener> T registerGlobalListener(T listener, Plugin plugin) {
		return registerGlobalListener(listener, plugin, EventPriority.NORMAL);
	}
	
	public static <T extends GlobalListener> T registerGlobalListener(T listener, Plugin plugin, EventPriority priority) {
		return registerGlobalListener(listener, plugin, priority, DEFAULT_GLOBAL_EXECUTOR);
	}
	
	public static <T extends GlobalListener> T registerGlobalListener(T listener, Plugin plugin, EventExecutor executor) {
		return registerGlobalListener(listener, plugin, EventPriority.NORMAL, executor);
	}
	
	public static <T extends GlobalListener> T registerGlobalListener(T listener, Plugin plugin, EventPriority priority, EventExecutor executor) {
		registerListener(listener, plugin);
		RegisteredListener registeredListener = new RegisteredListener(listener, executor, priority, plugin, false);
		GLOBAL_LISTENERS.add(registeredListener);
		ALL_HANDLER_LISTS.forEach(hl -> hl.register(registeredListener));
		return listener;
	}
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		return listener;
	}
	
	public static <T extends Listener> T registerListener(T listener, Plugin plugin, Predicate<Event> predicate) {
		Validate.notNull(plugin, "plugin cannot be null");
		Validate.notNull(listener, "listener cannot be null");
		Validate.notNull(predicate, "predicate cannot be null");
		Validate.isTrue(plugin.isEnabled(), "plugin attempted to register listener while not enabled");
		for(Method method : listener.getClass().getDeclaredMethods()) {
			EventHandler eventHandler = method.getAnnotation(EventHandler.class);
			if(eventHandler == null) continue;
			Class<?>[] parameters = method.getParameterTypes();
			if(parameters.length != 1) continue;
			Class<?> parameterClass = parameters[0];
			if(!Event.class.isAssignableFrom(parameterClass)) continue;
			Class<? extends Event> eventClass = parameterClass.asSubclass(Event.class);
			Class<? extends Event> handlerClass = getHandlerClass(eventClass);
			if(handlerClass == null) continue;
			method.setAccessible(true);
			EventExecutor executor = (listener1, event) -> {
				try {
					if(eventClass.isAssignableFrom(event.getClass()) && predicate.test(event)) method.invoke(listener1, event);
				} catch(InvocationTargetException e) {
					throw new EventException(e.getCause());
				} catch(Throwable e) {
					throw new EventException(e);
				}
			};
			getHandlerList(handlerClass).register(new RegisteredListener(listener, executor, eventHandler.priority(), plugin, eventHandler.ignoreCancelled()));
		}
		return listener;
	}
	
	public static void cancelEvents(Plugin plugin, Class<?>... classes) {
		cancelEvents(plugin, EventPriority.NORMAL, null, classes);
	}
	
	public static void cancelEvents(Plugin plugin, EventPriority priority, Class<?>... classes) {
		cancelEvents(plugin, priority, null, classes);
	}
	
	public static void cancelEvents(Plugin plugin, NoArgPredicate predicate, Class<?>... classes) {
		cancelEvents(plugin, EventPriority.NORMAL, predicate, classes);
	}
	
	public static void cancelEvents(Plugin plugin, EventPriority priority, NoArgPredicate predicate, Class<?>... classes) {
		Validate.isTrue(classes != null && classes.length > 0, "no event classes were provided");
		for(int i = 0; i < classes.length; i++) {
			Class<?> clazz = classes[i];
			if(clazz == null || !Event.class.isAssignableFrom(clazz) || !Cancellable.class.isAssignableFrom(clazz)) {
				plugin.getLogger().warning(String.format("the event class provided at index %s is null or not a cancellable event", i));
				continue;
			}
			Class<? extends Event> eventClass = clazz.asSubclass(Event.class);
			getHandlerList(eventClass).register(predicate == null ?
					new CancelRegisteredListener(priority, plugin) : new PredicateCancelRegisteredListener(priority, plugin, predicate));
		}
	}
	
	public static Class<? extends Event> getHandlerClass(Class<? extends Event> clazz) {
		Validate.notNull(clazz, "clazz cannot be null");
		try {
			clazz.getDeclaredMethod("getHandlerList");
			return clazz;
		} catch(NoSuchMethodException e) {
			Class<?> superClass = clazz.getSuperclass();
			if(superClass != null && !superClass.equals(Event.class) && Event.class.isAssignableFrom(superClass))
				return getHandlerClass(superClass.asSubclass(Event.class));
			return null;
		}
	}
	
	public static HandlerList getHandlerList(Class<? extends Event> clazz) {
		Class<? extends Event> handlerClass = getHandlerClass(clazz);
		Validate.notNull(handlerClass, "event %s does not have a static getHandlerList method", clazz.getSimpleName());
		return HANDLER_LIST_CACHE.computeIfAbsent(clazz, c -> (HandlerList)ReflectionHelper.getObjectFromStaticGetter(handlerClass, "getHandlerList"));
	}
	
}
