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
	public ECCommands cmdManager;
	public static Server server;
	
	public void onLoad()
	{
		new ECConfig(this);
		server = getServer();
		evtManager = new ECEventManager(this);
		evtHandler = new ECEventHandler();
		cmdManager = new ECCommands(this);
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
			if (args.length == 1 && args[0].equalsIgnoreCase("help"))
				return cmdManager.eventHelp(s);
			else if (args.length == 1 && args[0].equalsIgnoreCase("version"))
				return cmdManager.showVersion(s);
			else if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
				return cmdManager.reloadEvents(s);
			else if (args.length == 2 && args[0].equalsIgnoreCase("create"))
				return cmdManager.createEvent(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("confirm"))
				return cmdManager.confirmEvent(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("start"))
				return cmdManager.startEvent(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("queue") && args[1].equalsIgnoreCase("next"))
				return cmdManager.queueNext(s);
			else if (args.length == 2 && args[0].equalsIgnoreCase("queue") && args[1].equalsIgnoreCase("list"))
				return cmdManager.listQueue(s);
			else if (args.length == 2 && args[0].equalsIgnoreCase("setlength"))
				return cmdManager.setRoundLength(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("numlives"))
				return cmdManager.setNumLives(s, args);
			else if (args.length == 3 && args[0].equalsIgnoreCase("queue"))
				return cmdManager.modQueue(s, args);
			else if (args.length == 3 && args[0].equalsIgnoreCase("region"))
				return cmdManager.modRegions(s, args);
			else if (args.length == 4 && args[0].equalsIgnoreCase("date"))
				return cmdManager.addRemDate(s, args);
			else if (args.length >= 1 && args.length <= 2 && args[0].equalsIgnoreCase("info"))
				return cmdManager.getEventInfo(s, args);
			else if (args.length >= 1 && args.length <=2 && args[0].equalsIgnoreCase("setscramble"))
				return cmdManager.setScramble(s, args);
			else if (args.length >= 3 && args.length <= 4 && args[0].equalsIgnoreCase("tp"))
				return cmdManager.tp(s, args);
			else if (args.length >= 4 && args.length <= 6 && args[1].equalsIgnoreCase("add"))
				return cmdManager.addRegionFlag(s, args);
			else if (args.length >= 2 && args[0].equalsIgnoreCase("description"))
				return cmdManager.setDescription(s, args);
		}
		else if (cmd.getName().equalsIgnoreCase("tokens"))
		{
			s.sendMessage("Token commands do not work yet");
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("evt"))
		{
			if (args.length == 1 && args[0].equalsIgnoreCase("help"))
				return cmdManager.evtHelp(s);
			else if (args.length == 1 && args[0].equalsIgnoreCase("version"))
				return cmdManager.reloadConfig(s);
		}
		else if (cmd.getName().equalsIgnoreCase("team"))
		{
			if (args.length == 1 && args[0].equalsIgnoreCase("help"))
				return cmdManager.teamHelp(s);
			else if (args.length == 1 && args[0].equalsIgnoreCase("list"))
				return cmdManager.listTeams(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("add"))
				return cmdManager.addTeam(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("remove"))
				return cmdManager.removeTeam(s, args);
			else if (args.length == 2 && args[0].equalsIgnoreCase("sethat"))
				return cmdManager.setTeamHat(s, args);
			else if (args.length == 2 && args[1].equalsIgnoreCase("setspawn"))
				return cmdManager.setTeamSpawn(s, args);
			else if (args.length == 3 && args[1].equalsIgnoreCase("maxplayers"))
				return cmdManager.setTeamMaxplayers(s, args);
			else if (args.length == 3 && args[1].equalsIgnoreCase("ignorescramble"))
				return cmdManager.teamShouldScramble(s, args);
		}
		
		return false;
	}
}
