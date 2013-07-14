package com.andoutay.eventcenter;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;

public class ECConfig
{
	private static Configuration config;
	
	public static List<String> worldList;
	public static List<Integer> colorList;
	public static int tokenID, roundLen;
	public static List<Map<?, ?>> flagMsgs;
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
		tokenID = config.getInt("tokenID");
		roundLen = config.getInt("eventDefaults.roundLen");
		flagMsgs = config.getMapList("flagMsgs");
		colorList = config.getIntegerList("eventDefaults.colors");
	}
}
