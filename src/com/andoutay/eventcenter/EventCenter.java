package com.andoutay.eventcenter;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class EventCenter extends JavaPlugin
{
	public static Logger log = Logger.getLogger("Minecraft");
	public static String logPref = "[EventCenter] ";
	public static String chPref = ChatColor.YELLOW + logPref + ChatColor.RESET;
	public ECEventManager evtManager;
	public ECEventHandler evtHandler;
	public static Server server;
	
	public void onLoad()
	{
		new ECConfig(this);
		server = getServer();
		evtManager = new ECEventManager(this);
		evtHandler = new ECEventHandler();
	}
	
	public void onEnable()
	{
		ECConfig.onEnable();
		server.getPluginManager().registerEvents(evtHandler, this);
		
		log.info(logPref + "Enabled");
	}
	
	public void onDisable()
	{
		
		
		log.info(logPref + "Disabled");
	}
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("event"))
		{
			
		}
		else if (cmd.getName().equalsIgnoreCase("tokens"))
		{
			
		}
		else if (cmd.getName().equalsIgnoreCase("evt"))
		{
			
		}
		else if (cmd.getName().equalsIgnoreCase("team"))
		{
			
		}
		
		return false;
	}
}
