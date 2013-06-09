package com.andoutay.eventcenter;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

public class ECEventManager
{
	private EventCenter plugin;
	private File evtFile;
	private YamlConfiguration evts;
	private Logger log = Logger.getLogger("Minecraft");
	private String logPref;
	
	public ECEventManager(EventCenter plugin)
	{
		logPref = EventCenter.logPref;
		evtFile = new File(plugin.getDataFolder(), "events.yml");
		evts = YamlConfiguration.loadConfiguration(evtFile);
		this.plugin = plugin;
	}
	
	public void loadEvents()
	{
		//load events from file
	}
}
