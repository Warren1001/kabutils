package com.kabryxis.kabutils.spigot.plugin;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;

import com.avaje.ebean.EbeanServer;

public class SpoofPlugin implements Plugin {
	
	private static Plugin plugin;
	
	public static Plugin get() {
		if(plugin == null) plugin = new SpoofPlugin("Spoof");
		return plugin;
	}
	
	private final String name;
	private final Server server;
	private final PluginDescriptionFile pdf;
	private final PluginLoader loader;
	private final Logger logger;
	
	private SpoofPlugin(String name) {
		this.name = name;
		this.server = Bukkit.getServer();
		this.pdf = new PluginDescriptionFile(name, "1", getClass().getPackage().getName() + "." + getClass().getName());
		this.loader = new SpoofPluginLoader();
		this.logger = Logger.getLogger("SpoofPlugin");
		server.getPluginManager().enablePlugin(this);
	}
	
	@Override
	public void onLoad() {}
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Server getServer() {
		return server;
	}
	
	@Override
	public PluginDescriptionFile getDescription() {
		return pdf;
	}
	
	@Override
	public PluginLoader getPluginLoader() {
		return loader;
	}
	
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	/*
	 *
	 * end
	 * 
	 */
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}
	
	@Override
	public FileConfiguration getConfig() {
		return null;
	}
	
	@Override
	public File getDataFolder() {
		return null;
	}
	
	@Override
	public EbeanServer getDatabase() {
		return null;
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return null;
	}
	
	@Override
	public InputStream getResource(String filename) {
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isNaggable() {
		return false;
	}
	
	@Override
	public void reloadConfig() {}
	
	@Override
	public void saveConfig() {}
	
	@Override
	public void saveDefaultConfig() {}
	
	@Override
	public void saveResource(String resourcePath, boolean replace) {}
	
	@Override
	public void setNaggable(boolean canNag) {}
	
	private class SpoofPluginLoader implements PluginLoader {
		
		@Override
		public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
			return null;
		}
		
		@Override
		public void disablePlugin(Plugin plugin) {}
		
		@Override
		public void enablePlugin(Plugin plugin) {}
		
		@Override
		public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
			return null;
		}
		
		@Override
		public Pattern[] getPluginFileFilters() {
			return null;
		}
		
		@Override
		public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
			return null;
		}
		
	}
	
}
