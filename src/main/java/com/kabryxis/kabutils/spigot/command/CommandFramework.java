package com.kabryxis.kabutils.spigot.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

/**
 * Command Framework - CommandFramework <br>
 * The main command framework class used for controlling the framework.
 * 
 * @author minnymin3
 */
public class CommandFramework {
	
	private final Map<String, Entry<Method, Object>> commandMap = new HashMap<>();
	
	private final Plugin plugin;
	private final CommandMap map;
	private final String usagePrefix;
	private final String permissionMessage;
	
	/**
	 * Initializes the command framework and sets up the command maps
	 * 
	 * @param plugin
	 */
	public CommandFramework(Plugin plugin, String usagePrefix, String permissionMessage) {
		this.plugin = plugin;
		CommandMap map = null;
		if(plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			SimplePluginManager manager = (SimplePluginManager)plugin.getServer().getPluginManager();
			try {
				Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap)field.get(manager);
			}
			catch(IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		}
		this.map = map;
		this.usagePrefix = usagePrefix;
		this.permissionMessage = permissionMessage;
	}
	
	/**
	 * Handles commands. Used in the onCommand method in your JavaPlugin class
	 * 
	 * @param sender The {@link org.bukkit.command.CommandSender} parsed from
	 *            onCommand
	 * @param label The label parsed from onCommand
	 * @param cmd The {@link org.bukkit.command.Command} parsed from onCommand
	 * @param args The arguments parsed from onCommand
	 * @return Always returns true for simplicity's sake in onCommand
	 */
	public boolean handleCommand(CommandSender sender, String label, org.bukkit.command.Command cmd, String[] args) {
		for(int i = args.length; i >= 0; i--) {
			StringBuilder buffer = new StringBuilder();
			buffer.append(label.toLowerCase());
			for(int x = 0; x < i; x++) {
				buffer.append(".").append(args[x].toLowerCase());
			}
			String cmdLabel = buffer.toString();
			if(commandMap.containsKey(cmdLabel)) {
				Entry<Method, Object> entry = commandMap.get(cmdLabel);
				Command command = entry.getKey().getAnnotation(Command.class);
				if(!sender.hasPermission(command.permission())) {
					sender.sendMessage(permissionMessage);
					return true;
				}
				boolean b = false;
				try {
					b = (boolean)entry.getKey().invoke(entry.getValue(), new CommandArgs(sender, cmd, label, args, cmdLabel.split("\\.").length - 1));
				}
				catch(IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
				if(!b) sender.sendMessage(usagePrefix + command.name() + " " + command.usage());
				return true;
			}
		}
		defaultCommand(new CommandArgs(sender, cmd, label, args, 0));
		return true;
	}
	
	/**
	 * Registers all command and completer methods inside of the object. Similar
	 * to Bukkit's registerEvents method.
	 * 
	 * @param obj The object to register the commands of
	 */
	public void registerCommands(Object obj) {
		for(Method m : obj.getClass().getMethods()) {
			if(m.getAnnotation(Command.class) != null) {
				Command command = m.getAnnotation(Command.class);
				if(m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
					System.out.println("Unable to register command " + m.getName() + ". Unexpected method arguments");
					continue;
				}
				if(m.getReturnType() != boolean.class) {
					System.out.println("Unable to register command " + m.getName() + ". Unexpected return type");
					continue;
				}
				registerCommand(command, command.name(), m, obj);
				for(String alias : command.aliases()) {
					registerCommand(command, alias, m, obj);
				}
			}
		}
	}
	
	private void registerCommand(Command command, String label, Method m, Object obj) {
		Entry<Method, Object> entry = new AbstractMap.SimpleEntry<Method, Object>(m, obj);
		commandMap.put(label.toLowerCase(), entry);
		String cmdLabel = label.replace(".", ",").split(",")[0].toLowerCase();
		if(map.getCommand(cmdLabel) == null) {
			org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, plugin);
			map.register(plugin.getName(), cmd);
		}
		if(!command.description().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setDescription(command.description());
		}
		if(!command.usage().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setUsage(command.usage());
		}
	}
	
	private void defaultCommand(CommandArgs args) {
		args.getSender().sendMessage(args.getLabel() + " is not handled! Oh noes!");
	}
	
	/**
	 * Command Framework - Command <br>
	 * The command annotation used to designate methods as commands. All methods
	 * should have a single CommandArgs argument
	 * 
	 * @author minnymin3
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Command {
		
		/**
		 * The name of the command. If it is a sub command then its values would
		 * be separated by periods. ie. a command that would be a subcommand of
		 * test would be 'test.subcommandname'
		 * 
		 * @return
		 */
		public String name();
		
		/**
		 * Gets the required permission of the command
		 * 
		 * @return
		 */
		public String permission() default "";
		
		/**
		 * A list of alternate names that the command is executed under. See
		 * name() for details on how names work
		 * 
		 * @return
		 */
		public String[] aliases() default {};
		
		/**
		 * The description that will appear in /help of the command
		 * 
		 * @return
		 */
		public String description() default "";
		
		/**
		 * The usage that will appear in /help (commandname)
		 * 
		 * @return
		 */
		public String usage() default "";
	}
	
	/**
	 * Command Framework - BukkitCommand <br>
	 * An implementation of Bukkit's Command class allowing for registering of
	 * commands without plugin.yml
	 * 
	 * @author minnymin3
	 */
	class BukkitCommand extends org.bukkit.command.Command {
		
		private final Plugin owningPlugin;
		private final CommandExecutor executor;
		
		/**
		 * A slimmed down PluginCommand
		 * 
		 * @param label
		 * @param owner
		 */
		protected BukkitCommand(String label, Plugin owner) {
			super(label);
			this.executor = owner;
			this.owningPlugin = owner;
			this.usageMessage = "";
		}
		
		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			boolean success = false;
			
			if(!owningPlugin.isEnabled()) { return false; }
			
			if(!testPermission(sender)) { return true; }
			
			try {
				success = executor.onCommand(sender, this, commandLabel, args);
			}
			catch(Throwable ex) {
				throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + owningPlugin.getDescription().getFullName(), ex);
			}
			
			if(!success && usageMessage.length() > 0) {
				for(String line : usageMessage.replace("<command>", commandLabel).split("\n")) {
					sender.sendMessage(line);
				}
			}
			
			return success;
		}
		
	}
	
	/**
	 * Command Framework - CommandArgs <br>
	 * This class is passed to the command methods and contains various
	 * utilities as well as the command info.
	 * 
	 * @author minnymin3
	 */
	public class CommandArgs {
		
		private final CommandSender sender;
		private final org.bukkit.command.Command command;
		private final String label;
		private final String[] args;
		
		protected CommandArgs(CommandSender sender, org.bukkit.command.Command command, String label, String[] args, int subCommand) {
			String[] modArgs = new String[args.length - subCommand];
			System.arraycopy(args, 0 + subCommand, modArgs, 0, args.length - subCommand);
			
			StringBuilder buffer = new StringBuilder();
			buffer.append(label);
			for(int x = 0; x < subCommand; x++) {
				buffer.append(".").append(args[x]);
			}
			String cmdLabel = buffer.toString();
			this.sender = sender;
			this.command = command;
			this.label = cmdLabel;
			this.args = modArgs;
		}
		
		/**
		 * Gets the command sender
		 * 
		 * @return sender
		 */
		public CommandSender getSender() {
			return sender;
		}
		
		/**
		 * Gets the original command object
		 * 
		 * @return
		 */
		public org.bukkit.command.Command getCommand() {
			return command;
		}
		
		/**
		 * Gets the label including sub command labels of this command
		 * 
		 * @return Something like 'test.subcommand'
		 */
		public String getLabel() {
			return label;
		}
		
		/**
		 * Gets all the arguments after the command's label. ie. if the command
		 * label was test.subcommand and the arguments were subcommand foo foo,
		 * it would only return 'foo foo' because 'subcommand' is part of the
		 * command
		 * 
		 * @return
		 */
		public String[] getArgs() {
			return args;
		}
		
		public boolean isPlayer() {
			return sender instanceof Player;
		}
		
		public Player getPlayer() {
			return (Player)sender;
		}
	}
}
