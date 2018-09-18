package com.kabryxis.kabutils.spigot.command;

import com.kabryxis.kabutils.command.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BukkitCommandManager implements CommandManager {
	
	private final Map<Class<?>, Function<String, ?>> argumentConverters = new HashMap<>();
	private final Map<Class<?>, Function<CommandIssuer, ?>> issuerConverters = new HashMap<>();
	
	private final CommandMap commandMap;
	
	public BukkitCommandManager() {
		commandMap = hook();
		argumentConverters.put(int.class, Integer::parseInt);
		argumentConverters.put(Integer.class, Integer::parseInt);
		argumentConverters.put(double.class, Double::parseDouble);
		argumentConverters.put(Double.class, Double::parseDouble);
		argumentConverters.put(float.class, Float::parseFloat);
		argumentConverters.put(Float.class, Float::parseFloat);
		argumentConverters.put(long.class, Long::parseLong);
		argumentConverters.put(Long.class, Long::parseLong);
		argumentConverters.put(short.class, Short::parseShort);
		argumentConverters.put(Short.class, Short::parseShort);
		argumentConverters.put(byte.class, Integer::parseInt);
		argumentConverters.put(Byte.class, Integer::parseInt);
		argumentConverters.put(boolean.class, Boolean::parseBoolean);
		argumentConverters.put(Boolean.class, Boolean::parseBoolean);
		argumentConverters.put(Player.class, Bukkit::getPlayer);
		argumentConverters.put(Material.class, Material::getMaterial);
		issuerConverters.put(Player.class, issuer -> ((BukkitCommandIssuer)issuer).getPlayer());
	}
	
	@Override
	public <T> void registerArgumentConverter(Class<T> clazz, Function<String, T> converter) {
		argumentConverters.put(clazz, converter);
	}
	
	@Override
	public <T> void registerIssuerConverter(Class<T> clazz, Function<CommandIssuer, T> converter) {
		issuerConverters.put(clazz, converter);
	}
	
	@Override
	public CommandDataManager registerListener(Object listener) {
		if(listener.getClass().getAnnotation(Com.class) != null) {
			SubcommandHandler subcommandHandler = new SubcommandHandler(this, listener);
			registerCommandData(subcommandHandler);
			return subcommandHandler;
		}
		else {
			registerSubclassesAndMethods(this, listener);
			return this;
		}
	}
	
	@Override
	public void registerListeners(Object... listeners) {
		for(Object listener : listeners) {
			registerListener(listener);
		}
	}
	
	@Override
	public void registerSubclassesAndMethods(CommandDataManager dataManager, Object listener) {
		Class<?> commandClass = listener.getClass();
		for(Method method : commandClass.getDeclaredMethods()) {
			Com com = method.getAnnotation(Com.class);
			if(com != null) {
				String requiredArgs = com.args();
				boolean hasNullArguments = requiredArgs.contains(",") || requiredArgs.contains("-");
				if(method.getReturnType() != boolean.class && method.getReturnType() != void.class) throw new IllegalArgumentException("The command method '" + method.getName() +
						"' needs to have a return type of boolean or void.");
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length == 2 && CommandIssuer.class.isAssignableFrom(parameterTypes[0]) && parameterTypes[1] == String[].class)
					dataManager.registerCommandData(new SimpleCommand(this, listener, method));
				else {
					if(parameterTypes.length == 0) dataManager.registerCommandData(new ComplexCommand(this, listener, method));
					else {
						Class<?> parameterType = parameterTypes[0];
						if(!CommandIssuer.class.isAssignableFrom(parameterType)) {
							Function<CommandIssuer, ?> converter = issuerConverters.get(parameterType);
							if(converter == null) throw new IllegalArgumentException("Could not find CommandIssuer to " + parameterType.getName() + " converter for command method '" + method.getName() + "'.");
						}
						if(parameterTypes.length == 2) {
							if(hasNullArguments) throw new IllegalArgumentException("The command method '" + method.getName() +
									"' needs to have an int as second argument to provide argument length and have 3 or more arguments when using varied argument lengths.");
							else {
								parameterType = parameterTypes[1];
								if(parameterType != String.class) {
									Function<String, ?> converter = argumentConverters.get(parameterType);
									if(converter == null) throw new IllegalArgumentException("Could not find String to " + parameterType.getName() + " converter for command method '" + method.getName() + "'.");
								}
							}
						}
						else if(parameterTypes.length > 2) {
							if(hasNullArguments) {
								parameterType = parameterTypes[1];
								if(parameterType != int.class) throw new IllegalArgumentException("The command method '" + method.getName() + "' needs a second argument of int for a complex command.");
							}
							for(int i = hasNullArguments ? 2 : 1; i < parameterTypes.length; i++) {
								parameterType = parameterTypes[i];
								if(parameterType == String.class) continue;
								Function<String, ?> converter = argumentConverters.get(parameterType);
								if(converter == null) throw new IllegalArgumentException("Could not find String to " + parameterType.getName() + " converter for command method '" + method.getName() + "'.");
							}
						}
						dataManager.registerCommandData(new ComplexCommand(this, listener, method));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T convertIssuer(Class<T> clazz, CommandIssuer issuer) {
		return (T)(clazz == CommandIssuer.class || clazz.isInstance(issuer) ? issuer : issuerConverters.get(clazz).apply(issuer));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T convertArgument(Class<T> clazz, String arg) {
		return (T)(clazz == String.class ? arg : argumentConverters.get(clazz).apply(arg));
	}
	
	@Override
	public void registerCommandData(CommandData commandData) {
		commandMap.register(commandData.getName(), new BukkitCommandWrapper(commandData));
	}
	
	private CommandMap hook() {
		try {
			Server server = Bukkit.getServer();
			Method getCommandMapMethod = server.getClass().getDeclaredMethod("getCommandMap");
			getCommandMapMethod.setAccessible(true);
			return (CommandMap)getCommandMapMethod.invoke(server);
		} catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new IllegalStateException("Could not retrieve CommandMap from Server", e);
		}
	}
	
}
