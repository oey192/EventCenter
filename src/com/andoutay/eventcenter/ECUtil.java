package com.andoutay.eventcenter;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ECUtil
{
	public static WorldGuardPlugin getWG(EventCenter plugin)
	{
		PluginManager pm = plugin.getServer().getPluginManager();
		WorldGuardPlugin ans = (WorldGuardPlugin) pm.getPlugin("WorldGuard");
		if (ans == null)
		{
			EventCenter.log.severe(EventCenter.logPref + "Could not load WorldGuard. Unloading plugin");
			pm.disablePlugin(plugin);
		}
		return ans;
	}
	
	public static String implode(String[] str)
	{
		String ans = "";
		
		for (String temp : str)
			ans += temp + " ";
		
		ans = ans.substring(0, ans.length() - 1);
		
		return ans;
	}
	
	//returns true if suspicious is completely encapsulated by container, false otherwise
	public static boolean regionContainsRegion(ProtectedRegion container, ProtectedRegion suspicious)
	{
		return container.contains(suspicious.getMaximumPoint()) && container.contains(suspicious.getMinimumPoint());
	}
	
	//returns the appropriate chat color based on a user input string
	public static ChatColor chatColorForString(String colString) {
		ChatColor ans = ChatColor.RESET;
		
		if (colString.equalsIgnoreCase("red")) {
			ans = ChatColor.RED;
		} else if (colString.equalsIgnoreCase("dark red")) {
			ans = ChatColor.DARK_RED;
		} else if (colString.equalsIgnoreCase("orange") || colString.equalsIgnoreCase("gold")) {
			ans = ChatColor.GOLD;
		} else if (colString.equalsIgnoreCase("yellow")) {
			ans = ChatColor.YELLOW;
		} else if (colString.equalsIgnoreCase("green")) {
			ans = ChatColor.GREEN;
		} else if (colString.equalsIgnoreCase("dark green")) {
			ans = ChatColor.DARK_GREEN;
		} else if (colString.equalsIgnoreCase("aqua") || colString.equalsIgnoreCase("light blue")) {
			ans = ChatColor.AQUA;
		} else if (colString.equalsIgnoreCase("dark aqua")) {
			ans = ChatColor.DARK_AQUA;
		} else if (colString.equalsIgnoreCase("blue")) {
			ans = ChatColor.BLUE;
		} else if (colString.equalsIgnoreCase("dark blue") || colString.equalsIgnoreCase("indigo")) {
			ans = ChatColor.DARK_BLUE;
		} else if (colString.equalsIgnoreCase("light purple")) {
			ans = ChatColor.LIGHT_PURPLE;
		} else if (colString.equalsIgnoreCase("purple")) {
			ans = ChatColor.DARK_PURPLE;
		} else if (colString.equalsIgnoreCase("grey") || colString.equalsIgnoreCase("gray")) {
			ans = ChatColor.GRAY;
		} else if (colString.equalsIgnoreCase("dark grey") || colString.equalsIgnoreCase("dark gray")) {
			ans = ChatColor.DARK_GRAY;
		} else if (colString.equalsIgnoreCase("black")) {
			ans = ChatColor.BLACK;
		} else if (colString.equalsIgnoreCase("white")) {
			ans = ChatColor.WHITE;
		}
		
		return ans;
	}
	
	/**
	 * Returns true or false depending on whether the event contains a region that matches the given name (id)
	 * If the region does contain a name that matches, the regionName parameter is modified to contain the exact region name
	 * 
	 * @param 		evt - the event you want to search for the region
	 * @param		regionName - the name of the region you're looking for - potentially modified by the function
	 * @return 		whether the given event contains a region with the given name or not 
	 */
	public static boolean eventHasRegion(ECEvent evt, String regionName) {
		boolean ans = false;
		
		if (evt.mainRegion.getId().equalsIgnoreCase(regionName) || evt.mainRegion.getId().toLowerCase().contains(regionName.toLowerCase())) {
			regionName = evt.mainRegion.getId();
			ans = true;
		} else {
			for (ProtectedRegion pr : evt.getSubRegions()) {
				if (pr.getId().equalsIgnoreCase(regionName) || pr.getId().toLowerCase().contains(regionName.toLowerCase())) {
					regionName = pr.getId();
					ans = true;
					break;
				}
			}
		}
		
		return ans;
	}
}
