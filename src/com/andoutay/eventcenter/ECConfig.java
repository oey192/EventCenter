package com.andoutay.eventcenter;

import java.util.List;

import org.bukkit.configuration.Configuration;

public class ECConfig
{
	private static Configuration config;
	
	public static List<String> worldList;
	private static EventCenter plugin;
	
	ECConfig(EventCenter plugin)
	{
		ECConfig.plugin = plugin;
		config = plugin.getConfig().getRoot();
		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public static void onEnable()
	{
		loadConfigVals();
	}
	
	public static void reload()
	{
		plugin.reloadConfig();
		config = plugin.getConfig().getRoot();
		onEnable();
	}
	
	public static void loadConfigVals()
	{
		worldList = config.getStringList("worlds");
	}
}
