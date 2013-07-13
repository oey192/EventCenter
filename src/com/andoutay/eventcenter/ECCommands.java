package com.andoutay.eventcenter;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ECCommands
{
	private EventCenter plugin;
	private Server server;
	private HashMap<Player, ECEvent> selectedEvents;
	
	ECCommands(EventCenter plugin)
	{
		this.plugin = plugin;
		server = plugin.getServer();
		selectedEvents = new HashMap<Player, ECEvent>();
	}
	
	//commands
	public boolean addRegionFlag(CommandSender s, String[] args)
	{
		s.sendMessage("Adding region flag");
		return true;
	}
	
	public boolean addRemDate(CommandSender s, String[] args)
	{
		s.sendMessage("Adding or removing a single date or clearing all dates");
		return true;
	}
	
	public boolean addTeam(CommandSender s, String[] args)
	{
		s.sendMessage("Team added!");
		return true;
	}
	
	public boolean confirmEvent(CommandSender s, String[] args) {
		s.sendMessage("Event confirmed");
		return false;
	}
	
	public boolean createEvent(CommandSender s, String[] args)
	{
		s.sendMessage("Creating event");
		return true;
	}
	
	public boolean getEventInfo(CommandSender s, String[] args)
	{
		s.sendMessage("Here's some event info:");
		return true;
	}
	
	public boolean listQueue(CommandSender s)
	{
		s.sendMessage("Selected the event!");
		return true;
	}
	
	public boolean listTeams(CommandSender s, String[] args)
	{
		s.sendMessage("You! Have some teams!");
		return true;
	}
	
	public boolean modQueue(CommandSender s, String[] args)
	{
		s.sendMessage("Event added or removed from queue!");
		return true;
	}
	
	public boolean modRegions(CommandSender s, String[] args)
	{
		s.sendMessage("Setting main region or adding or deleting a sub-region");
		return true;
	}
	
	public boolean queueNext(CommandSender s)
	{
		s.sendMessage("Next event in queue started");
		return true;
	}
	
	public boolean reloadConfig(CommandSender s)
	{
		ECConfig.reload();
		s.sendMessage(EventCenter.chPref + "Config reloaded");
		return true;
	}
	
	public boolean reloadEvents(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Events reloaded");
		return true;
	}
	
	public boolean removeTeam(CommandSender s, String[] args)
	{
		s.sendMessage("Team removed!");
		return true;
	}
	
	public boolean selectEvent(CommandSender s, String[] args)
	{
		s.sendMessage("Selected the event!");
		return true;
	}
	
	public boolean setDescription(CommandSender s, String[] args)
	{
		s.sendMessage("Description set!");
		return true;
	}
	
	public boolean setNumLives(CommandSender s, String[] args)
	{
		s.sendMessage("Num lives set!");
		return true;
	}
	
	public boolean setRoundLength(CommandSender s, String[] args)
	{
		s.sendMessage("Round length set!");
		return true;
	}
	
	public boolean setScramble(CommandSender s, String[] args)
	{
		s.sendMessage("Scramble now set!");
		return true;
	}
	
	public boolean setTeamHat(CommandSender s, String[] args)
	{
		s.sendMessage("Hat for team now set!");
		return true;
	}
	
	public boolean setTeamMaxplayers(CommandSender s, String[] args)
	{
		s.sendMessage("Maxplayers now set or cleared!");
		return true;
	}
	
	public boolean setTeamSpawn(CommandSender s, String[] args)
	{
		s.sendMessage("Your team now knows where to spawn!");
		return true;
	}
	
	public boolean showVersion(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Current Version: " + plugin.getDescription().getVersion());
		return true;
	}
	
	public boolean startEvent(CommandSender s, String[] args)
	{
		s.sendMessage("Starting event");
		return true;
	}
	
	public boolean teamShouldScramble(CommandSender s, String[] args)
	{
		s.sendMessage("Team now set to scramble or not");
		return true;
	}
	
	public boolean tp(CommandSender s, String[] args)
	{
		s.sendMessage("Teleporting player to event");
		return true;
	}
	
	
	
	//Help commands
	public boolean eventHelp(CommandSender s)
	{
		s.sendMessage("Event Help:");
		return true;
	}
	
	public boolean evtHelp(CommandSender s)
	{
		s.sendMessage("Evt Help:");
		return true;
	}
	
	public boolean teamHelp(CommandSender s)
	{
		s.sendMessage("Team Help:");
		return true;
	}
	
	public boolean tokenHelp(CommandSender s)
	{
		s.sendMessage("Token Help:");
		return true;
	}
	
	
	//helper functions
	public boolean isRegion(String str)
	{
		//foreach region, return str.equalsIgnoreCase(region.name)
		return false;
	}
	
	public Player getPlayerForName(String partial)
	{
		Player player = null;
		boolean found = false, foundMult = false;

		player = server.getPlayer(partial);

		if (player == null)
			for (Player p : server.getOnlinePlayers())
				if (p.getDisplayName().toLowerCase().contains(partial.toLowerCase()))
				{
					if (found)
					{
						foundMult = true;
						break;
					}
					player = p;
					found = true;
				}

		if (foundMult)
			return null;

		return player;
	}

	public String getPlayerName(String partial)
	{
		Player temp = getPlayerForName(partial);
		if (temp == null)
			return server.getOfflinePlayer(partial).getName();
		return temp.getDisplayName();

		/*
		 * TODO add mChat support - Parser.parsePlayerName(sender.getName(),
		 * world); get Parser using ExternalJarHandler outlined at top of file
		 */
	}
	
	
	private boolean canUseCommand(CommandSender s, String perm)
	{
		return ((s instanceof ConsoleCommandSender) || ((Player)s).hasPermission(perm));
	}
	
	private boolean invalidArgument(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + "An invalid argument was supplied.");
		return true;
	}
	
	private boolean badNumOfArgs(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + "An improper number of arguments was supplied");
		return true;
	}
	
	private boolean notFound(String thing, CommandSender s)
	{
		s.sendMessage(ChatColor.RED + thing + " not found");
		return true;
	}
	
	private boolean noAccess(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + ((s instanceof ConsoleCommandSender) ? "That command cannot be used from the console" : "You do not have sufficient privileges to use that command"));
		return true;
	}
}
