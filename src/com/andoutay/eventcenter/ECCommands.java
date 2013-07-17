package com.andoutay.eventcenter;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ECCommands
{
	private EventCenter plugin;
	private Server server;
	private ECEventManager evtManager;
	private HashMap<CommandSender, ECEvent> selectedEvents;
	private HashMap<CommandSender, Boolean> confirmDelete;
	private HashMap<CommandSender, Boolean> confirmStop;
	private HashMap<CommandSender, Boolean> confirmNext;
	private HashMap<CommandSender, String> confirmData;
	
	ECCommands(EventCenter plugin)
	{
		this.plugin = plugin;
		server = plugin.getServer();
		evtManager = plugin.evtManager;
		selectedEvents = new HashMap<CommandSender, ECEvent>();
		confirmDelete = new HashMap<CommandSender, Boolean>();
		confirmStop = new HashMap<CommandSender, Boolean>();
		confirmNext = new HashMap<CommandSender, Boolean>();
		confirmData = new HashMap<CommandSender, String>();
	}
	
	//commands
	public boolean addItemToTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Added items to team loadout");
		return true;
	}
	
	public boolean addOp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.op.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		ECEvent evt = evtManager.events.get(args[1]);
		if (evt == null) return notFound ("Event", s);
		Player p = getPlayerForName(args[2]);
		if (p == null) return notFound("Player", s);
		
		evt.addOp(p);
		
		s.sendMessage(EventCenter.chPref + "Operator added");
		return true;
	}
	
	public boolean addRegionFlag(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Adding region flag");
		return true;
	}
	
	public boolean addRemDate(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Adding or removing a single date or clearing all dates");
		return true;
	}
	
	public boolean addSubRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		if (selectedEvents.get(s).mainRegion == null)
		{
			s.sendMessage(ChatColor.RED + "The selected event does not have a main region. Sub regions cannot be added until a main region has been specified");
			return true;
		}
		
		//TODO: Add multiworld support and integration with config
		ProtectedRegion rg = ECUtil.getWG(plugin).getRegionManager(server.getWorld("world")).getRegion(args[2]);
		if (rg == null) return notFound("Region", s);
		
		if (ECUtil.regionContainsRegion(selectedEvents.get(s).mainRegion, rg))
		{
			selectedEvents.get(s).addSubRegion(rg);
			s.sendMessage(EventCenter.chPref + "Region added to " + selectedEvents.get(s).getName());
		}
		else
			s.sendMessage(ChatColor.RED + "That region is not contained by the main region for " + selectedEvents.get(s).getName());
		
		return true;
	}
	
	public boolean addTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Team added!");
		return true;
	}
	
	public boolean announceQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.queue.announce")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Next event in queue announced!");
		return true;
	}
	
	public boolean approvePending(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.remove") && !hasPerm(s, "eventcenter.event.run") && !hasPerm(s, "eventcenter.queue.next")) return noAccess(s);
		
		if ((confirmDelete.containsKey(s) && confirmDelete.get(s)) && (confirmStop.containsKey(s) && confirmStop.get(s)) && (confirmNext.containsKey(s) && confirmNext.get(s)))
		{
			s.sendMessage(ChatColor.RED + "An unexpected error occurred. Your pending command has been canceled. Please try again");
			confirmDelete.put(s, false);
			confirmStop.put(s, false);
			confirmNext.put(s, false);
			return true;
		}
		
		if (confirmDelete.containsKey(s) && confirmDelete.get(s))
		{
			if (!hasPerm(s, "eventcenter.event.remove")) return noAccess(s);
			
			if (selectedEvents.containsKey(s) && selectedEvents.get(s).getName().equals(confirmData.get(s))) selectedEvents.remove(s);
			evtManager.events.remove(confirmData.get(s));
			s.sendMessage(EventCenter.chPref + confirmData.get(s) + " deleted successfully");
			confirmDelete.put(s, false);
		}
		else if (confirmStop.containsKey(s) && confirmStop.get(s))
		{
			if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
			
			s.sendMessage(EventCenter.chPref + (evtManager.stopEvent() ? "Event stopped" : (ChatColor.RED + "Could not stop event")));
			confirmStop.put(s, false);
		}
		else if (confirmNext.containsKey(s) && confirmNext.get(s))
		{
			if (!hasPerm(s, "eventcenter.queue.next")) return noAccess(s);
			
			s.sendMessage(EventCenter.chPref + (evtManager.nextEvent() ? "Next event started" : ChatColor.RED + "Queue empty"));
			confirmNext.put(s, false);
		}
		else
			s.sendMessage("Nothing to apply");
		
		return true;
	}
	
	public boolean cancelPending(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.remove") && !hasPerm(s, "eventcenter.event.run") && !hasPerm(s, "eventcenter.queue.next")) return noAccess(s);
		
		if ((confirmDelete.containsKey(s) && confirmDelete.get(s)) || (confirmStop.containsKey(s) && confirmStop.get(s)) || (confirmNext.containsKey(s) && confirmNext.get(s)))
		{
			confirmDelete.put(s, false);
			confirmStop.put(s, false);
			confirmNext.put(s, false);
			s.sendMessage(EventCenter.chPref + "Command canceled");
		}
		else
			s.sendMessage(ChatColor.RED + "Nothing to cancel");
		return true;
	}
	
	public boolean confirmEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Event confirmed");
		return true;
	}
	
	public boolean createEvent(CommandSender s, String[] args)
	{
		if ((s instanceof ConsoleCommandSender) || !hasPerm(s, "eventcenter.event.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		if (!evtManager.events.containsKey(args[1]))
		{
			evtManager.events.put(args[1], new ECEvent(args[1], (Player)s));
			s.sendMessage(EventCenter.chPref + "Event created successfully. Use /event help for commands to modify the event");
		}
		else
			s.sendMessage(EventCenter.chPref + ChatColor.RED + "An event with that name already exists");
		return true;
	}
	
	public boolean delRegionFlag(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Region flag removed!");
		return true;
	}
	
	public boolean deQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.queue.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Event removed from queue!");
		return true;
	}
	
	public boolean ecAllChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.allchat")) return noAccess(s);
		
		s.sendMessage("ECs can broadcast ALL the things!");
		return true;
	}
	
	public boolean ecChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.ec")) return noAccess(s);
		
		s.sendMessage("Have some EC-only chat!");
		return true;
	}
	
	public boolean endRound(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage(EventCenter.chPref + (evtManager.endRound() ? "Ended round successfully" : "No active round to end"));
		return true;
	}
	
	public boolean enQueue(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.queue.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Event added to queue!");
		return true;
	}
	
	public boolean evtChat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.chat.send")) return noAccess(s);
		
		//also check if player is in appropriate world and is currently in an event
		
		s.sendMessage("Some event only chat!");
		return true;
	}
	
	public boolean getEventInfo(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.info")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Here's some event info:");
		return true;
	}
	
	public boolean joinEvent(CommandSender s)
	{
		if ((s instanceof ConsoleCommandSender) || !hasPerm(s, "eventcenter.join")) return noAccess(s);
		
		s.sendMessage("Congratulations, you are now in the event!");
		return true;
	}
	
	public boolean listEvents(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.list")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		if (evtManager.events.isEmpty())
			s.sendMessage(EventCenter.chPref + "No events exist");
		else
		{
			s.sendMessage(EventCenter.chPref + "Events:");
			//TODO: add pagination, possibly add other event info -- make the -- only show up if there is a description
			for (ECEvent evt : evtManager.events.values())
				s.sendMessage(evt.getName() + (evt.description.equalsIgnoreCase("") ? " -- " + evt.description : ""));
		}
		return true;
	}
	
	public boolean listItemsForTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.list")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Have some loadout items!");
		return true;
	}
	
	public boolean listQueue(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.queue.view")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		if (evtManager.queue.isEmpty())
			s.sendMessage(EventCenter.chPref + "Queue is empty");
		else
		{
			s.sendMessage(EventCenter.chPref + "Queue:");
			//TODO: Add pagination and/or more info for each event
			for (ECEvent evt : evtManager.queue.getQueue())
				s.sendMessage(evt.getName());
		}
		return true;
	}
	
	public boolean listTeams(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.list")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("You! Have some teams!");
		return true;
	}
	
	public boolean queueNext(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.queue.next")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Next event in queue started! Normally you will have to enter this command twice");
		return true;
	}
	
	public boolean reloadConfig(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.reload.config")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		ECConfig.reload();
		s.sendMessage(EventCenter.chPref + "Config reloaded");
		return true;
	}
	
	public boolean reloadEvents(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.reload.events")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage(EventCenter.chPref + "Events reloaded");
		return true;
	}
	
	public boolean removeEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		if (!evtManager.events.containsKey(args[1])) return notFound("Event", s);
		
		confirmDelete.put(s, true);
		confirmData.put(s, args[1]);
		
		s.sendMessage(EventCenter.chPref + "Are you sure you want to delete " + args[1] + "? If you'd like to remove that event's regions first, use /event remove regions <eventName>. Confirm this action with /event apply. Cancel with /event cancel");
		
		return true;
	}
	
	public boolean removeEventRegions(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Regions now gone!");
		return true;
	}
	
	public boolean removeItemFromTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Removed an item from team loadout");
		return true;
	}
	
	public boolean removeOp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.op.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Operator removed");
		return true;
	}
	
	public boolean removeSubRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Removed sub region from event");
		return true;
	}
	
	public boolean removeTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.remove")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Team removed!");
		return true;
	}
	
	public boolean scrambleTeams(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Teams scrambled!");
		return true;
	}
	
	public boolean selectEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		if (!evtManager.events.containsKey(args[1])) return notFound("Event", s);
		
		selectedEvents.put(s, evtManager.events.get(args[1]));
		
		s.sendMessage(EventCenter.chPref + "Event selected");
		return true;
	}
	
	public boolean setChatColor(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("ChatColor set!");
		return true;
	}
	
	public boolean setDefaultRoundLength(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Setting default round length");
		return true;
	}
	
	public boolean setDescription(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		selectedEvents.get(s).description = ECUtil.implode(Arrays.copyOfRange(args, 1, args.length));
		s.sendMessage(EventCenter.chPref + "Description set!");
		return true;
	}
	
	public boolean setFlagMessage(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Flag message set!");
		return true;
	}
	
	public boolean setFlagPoints(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Points for flag set!");
		return true;
	}
	
	public boolean setFlagSwitchTeam(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Teamswitch status for flag updated!");
		return true;
	}
	
	public boolean setFlagWin(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.flag.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Win status for flag updated!");
		return true;
	}
	
	public boolean setMainRegion(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.region.add")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		//Check the world that the player is in IF that world is on the acceptable list of worlds
		//If it isn't, loop through list of worlds trying to find it.
		//If more than one is found, alert user that they need to specify a world
		ProtectedRegion rg = ECUtil.getWG(plugin).getRegionManager(server.getWorld("world")).getRegion(args[2]);
		if (rg == null) return notFound("Region", s);
		
		selectedEvents.get(s).mainRegion = rg;
		
		s.sendMessage(EventCenter.chPref + "Set main region for event");
		return true;
	}
	
	public boolean setNumLives(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Num lives set!");
		return true;
	}
	
	public boolean setRoundLength(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Round length set!");
		return true;
	}
	
	public boolean setScramble(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Scramble now set!");
		return true;
	}
	
	public boolean setTeamHat(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Hat for team now set!");
		return true;
	}
	
	public boolean setTeamMaxplayers(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Maxplayers now set or cleared!");
		return true;
	}
	
	public boolean setTeamSpawn(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Your team now knows where to spawn!");
		return true;
	}
	
	public boolean showVersion(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.version")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage(EventCenter.chPref + "Current Version: " + plugin.getDescription().getVersion());
		return true;
	}
	
	public boolean startEvent(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Starting event");
		return true;
	}
	
	public boolean stopEvent(CommandSender s)
	{
		if (!hasPerm(s, "eventcenter.event.run")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Stopping event. This command normally has to be entered twice");
		return true;
	}
	
	public boolean teamShouldScramble(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.event.team.edit")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		if (selectedEvents.get(s) == null) return noEventSelected(s);
		
		s.sendMessage("Team now set to scramble or not");
		return true;
	}
	
	public boolean tp(CommandSender s, String[] args)
	{
		if (!hasPerm(s, "eventcenter.tp")) return noAccess(s);
		if (isCommandPending(s)) return pendingCommand(s);
		
		s.sendMessage("Teleporting player to event");
		return true;
	}
	
	
	
	//Help commands
	public boolean eventHelp(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Event Help:");
		return true;
	}
	
	public boolean evtHelp(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Evt Help:");
		return true;
	}
	
	public boolean teamHelp(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Team Help:");
		return true;
	}
	
	public boolean tokenHelp(CommandSender s)
	{
		s.sendMessage(EventCenter.chPref + "Token Help:");
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
	
	private boolean isCommandPending(CommandSender s)
	{
		return ((confirmDelete.containsKey(s) && confirmDelete.get(s)) || (confirmStop.containsKey(s) && confirmStop.get(s)) || (confirmNext.containsKey(s) && confirmNext.get(s)));
	}
	
	private boolean pendingCommand(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + "You have a pending command. Use /event apply or /event cancel to approve or cancel the pending command");
		return true;
	}
	
	private boolean invalidArgument(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + "An invalid argument was supplied.");
		return true;
	}
	
	private boolean noEventSelected(CommandSender s)
	{
		s.sendMessage(ChatColor.RED + "That command cannot be used unless an event is selected. Use /event select <name> to select an event");
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
