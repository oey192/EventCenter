package com.andoutay.eventcenter;

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
}
