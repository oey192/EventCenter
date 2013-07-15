package com.andoutay.eventcenter;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

public class ECEventManager
{
	private EventCenter plugin;
	private File evtFile;
	private YamlConfiguration evts;
	private Logger log = Logger.getLogger("Minecraft");
	private String logPref;
	private ECEvent runningEvt;
	public ECQueue queue;
	public HashMap<String, ECEvent> events;
	
	public ECEventManager(EventCenter plugin)
	{
		logPref = EventCenter.logPref;
		evtFile = new File(plugin.getDataFolder(), "events.yml");
		evts = YamlConfiguration.loadConfiguration(evtFile);
		queue = new ECQueue();
		this.plugin = plugin;
	}
	
	public void loadEvents()
	{
		events = new HashMap<String, ECEvent>();
		//load events from file
		//foreach thing in file
		//events.add(new Event(lots, of, stuff))
	}
	
	public void saveEvents()
	{
		//you know what to do
	}
	
	//starts the event with the appropriate name
	public boolean startEvent(String name)
	{
		if (eventIsRunning() || !events.containsKey(name)) return false;
		
		runningEvt = events.get(name);
		runningEvt.startEvent();
		return true;
	}
	
	//stops whatever event is currently running
	public boolean stopEvent()
	{
		if (!eventIsRunning()) return false;
		
		runningEvt.stopEvent();
		runningEvt = null;
		return true;
	}
	
	public boolean nextEvent()
	{
		stopEvent();
		if (queue.isEmpty()) return false;
		startEvent(queue.deQueue().getName());
		return false;
	}
	
	//starts a new round in the currently running event
	public boolean startRound()
	{
		if (!eventIsRunning() && !rip()) return false;
		
		runningEvt.startRound();
		return true;
	}
	
	//ends the round of whatever event is currently running
	public boolean endRound()
	{
		if (!eventIsRunning() && !rip()) return false;
		
		runningEvt.stopRound();
		return true;
	}
	
	//returns true if an event is running, false otherwise
	public boolean eventIsRunning()
	{
		return (runningEvt == null) ? false : runningEvt.isRunning();
	}
	
	public boolean rip()
	{
		return (runningEvt == null) ? false : runningEvt.rip();
	}
}
