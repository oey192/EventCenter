package com.andoutay.eventcenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ECEvent
{
	private Logger log = Logger.getLogger("Minecraft");
	
	
	private String name;
	public String description;
	private Player creator;
	private List<Player> operators;
	public ProtectedRegion mainRegion;
	private List<ProtectedRegion> subRegions;
	private HashMap<String, ECTeam> teams;
	private List<ECFlag> flags;
	private List<ECRecurDate> occurrences;
	public int defRoundLen;
	public int roundLen;
	private boolean running;
	private boolean rip;
	
	ECEvent(String name, Player creator)
	{
		this.name = name;
		description = "";
		this.creator = creator;
		operators = new ArrayList<Player>();
		operators.add(creator);
		teams = new HashMap<String, ECTeam>();
		running = false;
		rip = false;
		defRoundLen = 0;
		roundLen = defRoundLen;
	}
	
	ECEvent(String name, String description, Player creator, List<Player> operators, ProtectedRegion mainRegion, List<ProtectedRegion> subRegions, HashMap<String, ECTeam> teams, List<ECFlag> flags, List<ECRecurDate> occurrences, int defRoundLen)
	{
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.operators = operators;
		this.mainRegion = mainRegion;
		this.subRegions = subRegions;
		this.teams = teams;
		this.flags = flags;
		this.occurrences = occurrences;
		this.defRoundLen = defRoundLen;
		this.roundLen = defRoundLen;
		this.running = false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getCreator()
	{
		return creator.getDisplayName();
	}
	
	public boolean rip()
	{
		return rip;
	}
	
	public void addSubRegion(ProtectedRegion rg)
	{
		if (!subRegions.contains(rg)) subRegions.add(rg);
	}
	
	public void removeSubRegion(ProtectedRegion rg)
	{
		if (subRegions.contains(rg)) subRegions.remove(subRegions.indexOf(rg));
	}
	
	public void setRoundLen(int len)
	{
		roundLen = (len < 0) ? defRoundLen : len;
	}
	
	public void addItem(String name, ItemStack item)
	{
		/*fix later
		if (teams.get(name) == null) teams.put(name, new ArrayList<ItemStack>());
		teams.get(name).add(item);*/
	}
	
	public void removeItem(String name, ItemStack item)
	{
		/*
		if (teams.get(name) == null) return;
		
		Iterator<ItemStack> iter = teams.get(name).iterator();
		ItemStack temp;
		while (iter.hasNext())
		{
			temp = iter.next();
			if (temp.getType().equals(item.getType()))
				iter.remove();
		}
		*/
	}
	
	public void startEvent()
	{
		this.running = true;
		startRound();
	}
	
	//If teams are not set up, sets up teams and then starts event clock (if necessary)
	//Also announces to players in the event that it has begun
	public void startRound()
	{
		rip = true;
	}
	
	//Stops the clock, resets it, displays message to all participant that round is over, stops tracking of events, displays winners to participants
	//leaves teams intact
	public void stopRound()
	{
		
		
		rip = false;
	}
	
	public void stopEvent()
	{
		stopRound();
		this.running = false;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	/*private String name;
	private Vector pos1, pos2;
	private Location tempPos1, tempPos2;
	private boolean running;
	private String manager;
	private List<String> assistants;
	private String creator;
	private int numTeams;
	private HashMap<ChatColor, List<Player>> teams;
	private List<String> flags;
	boolean recurring;
	private HashMap<String, Integer> dow;	//Days of week that it repeats on mapping to the times it occurs on those days
	private int defaultTimeout;
	private int actualTimeout;
	private long startTime;
	private ChatColor[] colorArr;
	
	public ECEvent(String name, int numTeams, String creator)
	{
		Random r = new Random();
		ChatColor colors[] = ChatColor.values();
		int temp;
		boolean done = false;
		
		this.name = name;
		this.creator = creator;
		this.numTeams = numTeams;
		
		tempPos1 = null;
		tempPos2 = null;
		
		//do sthg if numTeams == 0
		
		teams = new HashMap<ChatColor, List<Player>>();
		for (int i = 0; i < numTeams; i++)
		{
			done = false;
			while (!done)
			{
				temp = r.nextInt();
				if (!(temp == 0 || temp == 1 || temp == 7 || temp == 8 || temp >= 15))
				{
					done = true;
					teams.put(colors[i], new ArrayList<Player>());
					colorArr[i] = colors[i];
				}
			}
		}
		EventCenter.log.info(EventCenter.logPref + "numTeams: " + numTeams + ", size of HashMap: " + teams.size());
		flags = new ArrayList<String>();
		
		running = false;
		manager = "";
		defaultTimeout = 0;
	}
	
	public void addPos1(Location pos)
	{
		tempPos1 = pos;
		if (tempPos2 != null)
			normalizeCorners();
	}
	
	public void addPos2(Location pos)
	{
		tempPos2 = pos;
		if (tempPos1 != null)
			normalizeCorners();
	}
	
	private void normalizeCorners()
	{
		if (tempPos1 == null || tempPos2 == null)
		{
			log.severe(EventCenter.logPref + "Internal logic error. Please tell developer to write better code");
			return;
		}
		
		int minx = tempPos1.getBlockX();
		int miny = tempPos1.getBlockY();
		int minz = tempPos1.getBlockZ();
		int maxx = minx;
		int maxy = miny;
		int maxz = minz;
		
		if (tempPos2.getBlockX() < minx)
			minx = tempPos2.getBlockX();
		else if (tempPos2.getBlockX() > maxx)
			maxx = tempPos2.getBlockX();
		
		if (tempPos2.getBlockY() < miny)
			miny = tempPos2.getBlockY();
		else if (tempPos2.getBlockY() > maxy)
			maxy = tempPos2.getBlockY();
		
		if (tempPos2.getBlockZ() < minz)
			minz = tempPos2.getBlockZ();
		else if (tempPos2.getBlockZ() > maxz)
			maxz = tempPos2.getBlockZ();
		
		pos1 = new Vector(minx, miny, minz);
		pos2 = new Vector(maxx, maxy, maxz);
	}
	
	public void setScheduleInfo(boolean recurring, HashMap<String, Integer> dow)
	{
		this.recurring = recurring;
		if (recurring)
			this.dow = dow;
	}
	
	public HashMap<String, Integer> getScheduleInfo()
	{
		if (!recurring) return null;
		
		return dow;
	}
	
	public void startEvent(String manager, List<String> assistants, Player[] players, int specialTimeout)
	{
		this.running = true;
		this.manager = manager;
		this.assistants = assistants;
		Random r = new Random();
		
		//going to need some sort of error in here if there aren't enough players per team
		for (Player p: players)
			//get a random team from 0 to the number of teams and add the current player to it
			teams.get(r.nextInt(numTeams)).add(p);
		
		if (specialTimeout == -1)
			actualTimeout = defaultTimeout;
		else
			actualTimeout = specialTimeout;
			
		startTime = System.currentTimeMillis();
	}*/
	
	
}
