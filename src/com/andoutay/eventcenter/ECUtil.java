package com.andoutay.eventcenter;

import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

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
}
