package com.andoutay.eventcenter;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
	public boolean addItemToTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Added items to team loadout");
		return true;
	}
	
	public boolean addOp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.op.add"))
			return noAccess(s);
		
		s.sendMessage("Operator added");
		return true;
	}
	
	public boolean addRegionFlag(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.add"))
			return noAccess(s);
		
		s.sendMessage("Adding region flag");
		return true;
	}
	
	public boolean addRemDate(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Adding or removing a single date or clearing all dates");
		return true;
	}
	
	public boolean addSubRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.add"))
			return noAccess(s);
		
		s.sendMessage("Added sub region to event");
		return true;
	}
	
	public boolean addTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.add"))
			return noAccess(s);
		
		s.sendMessage("Team added!");
		return true;
	}
	
	public boolean announceQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.queue.announce"))
			return noAccess(s);
		
		s.sendMessage("Next event in queue announced!");
		return true;
	}
	
	public boolean confirmEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.run"))
			return noAccess(s);
		
		s.sendMessage("Event confirmed");
		return false;
	}
	
	public boolean createEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.new"))
			return noAccess(s);
		
		s.sendMessage("Creating event");
		return true;
	}
	
	public boolean delRegionFlag(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.remove"))
			return noAccess(s);
		
		s.sendMessage("Region flag removed!");
		return true;
	}
	
	public boolean deQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.queue.remove"))
			return noAccess(s);
		
		s.sendMessage("Event removed from queue!");
		return true;
	}
	
	public boolean ecAllChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.allchat"))
			return noAccess(s);
		
		s.sendMessage("ECs can broadcast ALL the things!");
		return true;
	}
	
	public boolean ecChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.ec"))
			return noAccess(s);
		
		s.sendMessage("Have some EC-only chat!");
		return true;
	}
	
	public boolean enQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.queue.add"))
			return noAccess(s);
		
		s.sendMessage("Event added to queue!");
		return true;
	}
	
	public boolean evtChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.send"))
			return noAccess(s);
		
		//also check if player is in appropriate world and is currently in an event
		
		s.sendMessage("Some event only chat!");
		return true;
	}
	
	public boolean getEventInfo(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.info"))
			return noAccess(s);
		
		s.sendMessage("Here's some event info:");
		return true;
	}
	
	public boolean joinEvent(CommandSender s)
	{
		if ((s instanceof ConsoleCommandSender) || !hasPerm(s, "eventcenter.join"))
			return noAccess(s);
		
		s.sendMessage("Congratulations, you are now in the event!");
		return true;
	}
	
	public boolean listItemsForTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.list"))
			return noAccess(s);
		
		s.sendMessage("Have some loadout items!");
		return true;
	}
	
	public boolean listQueue(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.queue.view"))
			return noAccess(s);
		
		s.sendMessage("Listing the queue!");
		return true;
	}
	
	public boolean listTeams(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.list"))
			return noAccess(s);
		
		s.sendMessage("You! Have some teams!");
		return true;
	}
	
	public boolean queueNext(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.queue.next"))
			return noAccess(s);
		
		s.sendMessage("Next event in queue started! Normally you will have to enter this command twice");
		return true;
	}
	
	public boolean reloadConfig(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.reload.config"))
			return noAccess(s);
		
		ECConfig.reload();
		s.sendMessage(EventCenter.chPref + "Config reloaded");
		return true;
	}
	
	public boolean reloadEvents(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.reload.events"))
			return noAccess(s);
		
		s.sendMessage(EventCenter.chPref + "Events reloaded");
		return true;
	}
	
	public boolean removeEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.remove"))
			return noAccess(s);
		
		//Player has to enter command twice. After player enters command the first time, let them know what they are about to do, and inform them of the /event remove regions <evtName> command
		
		s.sendMessage("Removed event! Normally you will have to enter this twice");
		return true;
	}
	
	public boolean removeEventRegions(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.remove"))
			return noAccess(s);
		
		s.sendMessage("Regions now gone!");
		return true;
	}
	
	public boolean removeItemFromTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Removed an item from team loadout");
		return true;
	}
	
	public boolean removeOp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.op.remove"))
			return noAccess(s);
		
		s.sendMessage("Operator removed");
		return true;
	}
	
	public boolean removeSubRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.remove"))
			return noAccess(s);
		
		s.sendMessage("Removed sub region from event");
		return true;
	}
	
	public boolean removeTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.remove"))
			return noAccess(s);
		
		s.sendMessage("Team removed!");
		return true;
	}
	
	public boolean selectEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Selected the event!");
		return true;
	}
	
	public boolean setChatColor(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("ChatColor set!");
		return true;
	}
	
	public boolean setDefaultRoundLength(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Setting default round length");
		return true;
	}
	
	public boolean setDescription(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Description set!");
		return true;
	}
	
	public boolean setFlagMessage(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit"))
			return noAccess(s);
		
		s.sendMessage("Flag message set!");
		return true;
	}
	
	public boolean setFlagPoints(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit"))
			return noAccess(s);
		
		s.sendMessage("Points for flag set!");
		return true;
	}
	
	public boolean setFlagSwitchTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit"))
			return noAccess(s);
		
		s.sendMessage("Teamswitch status for flag updated!");
		return true;
	}
	
	public boolean setFlagWin(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit"))
			return noAccess(s);
		
		s.sendMessage("Win status for flag updated!");
		return true;
	}
	
	public boolean setMainRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.add"))
			return noAccess(s);
		
		WorldGuardPlugin wg = ECUtil.getWG(plugin);
		RegionManager rm = wg.getRegionManager(server.getWorld("world"));
		ProtectedRegion rg = rm.getRegion("test");
		if (rg == null) return notFound("Region", s);
		
		s.sendMessage(rg.toString());
		
		s.sendMessage("Set main region for event");
		return true;
	}
	
	public boolean setNumLives(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Num lives set!");
		return true;
	}
	
	public boolean setRoundLength(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Round length set!");
		return true;
	}
	
	public boolean setScramble(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit"))
			return noAccess(s);
		
		s.sendMessage("Scramble now set!");
		return true;
	}
	
	public boolean setTeamHat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Hat for team now set!");
		return true;
	}
	
	public boolean setTeamMaxplayers(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Maxplayers now set or cleared!");
		return true;
	}
	
	public boolean setTeamSpawn(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Your team now knows where to spawn!");
		return true;
	}
	
	public boolean showVersion(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.version"))
			return noAccess(s);
		
		s.sendMessage(EventCenter.chPref + "Current Version: " + plugin.getDescription().getVersion());
		return true;
	}
	
	public boolean startEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.run"))
			return noAccess(s);
		
		s.sendMessage("Starting event");
		return true;
	}
	
	public boolean stopEvent(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.run"))
			return noAccess(s);
		
		s.sendMessage("Stopping event. This command normally has to be entered twice");
		return true;
	}
	
	public boolean teamShouldScramble(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit"))
			return noAccess(s);
		
		s.sendMessage("Team now set to scramble or not");
		return true;
	}
	
	public boolean tp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.tp"))
			return noAccess(s);
		
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
	
	
	private boolean hasPerm(CommandSender s, String perm)
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
