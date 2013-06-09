package com.andoutay.eventcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ECEvent
{
	private String name;
	private Location corner1, corner2;
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
	
	public ECEvent(String name, Location one, Location two, int numTeams, String creator)
	{
		Random r = new Random();
		ChatColor colors[] = ChatColor.values();
		int temp;
		boolean done = false;
		
		this.name = name;
		this.corner1 = one;
		this.corner2 = two;
		this.creator = creator;
		this.numTeams = numTeams;
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
	}
	
	
}
