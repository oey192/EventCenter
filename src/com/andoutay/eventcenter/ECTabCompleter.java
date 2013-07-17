package com.andoutay.eventcenter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ECTabCompleter
{
	public static List<String> getEventCommands(CommandSender s, EventCenter plugin)
	{
		Server server = plugin.getServer();
		List<String> ans = new ArrayList<String>();
		if (hasPerm(s, "eventcenter.event.add")) ans.add("create");
		if (hasPerm(s, "eventcenter.event.remove")) ans.add("remove");
		if (hasPerm(s, "eventcenter.event.remove") || hasPerm(s, "eventcenter.event.run") || hasPerm(s, "eventcenter.queue.next")) ans.add("cancel");
		if (hasPerm(s, "eventcenter.event.remove") || hasPerm(s, "eventcenter.event.run") || hasPerm(s, "eventcenter.queue.next")) ans.add("apply");
		if (hasPerm(s, "eventcenter.event.list")) ans.add("list");
		
		
		if (hasPerm(s, "eventcenter.event.flag.add") || hasPerm(s, "eventcenter.event.flag.remove") || hasPerm(s, "eventcenter.event.flag.edit"))
		{
			for(String str : ECConfig.worldList)
			{
				World w = server.getWorld(str);
				RegionManager rm = ECUtil.getWG(plugin).getRegionManager(w);
				for (String rgStr : rm.getRegions().keySet())
					ans.add(rgStr);

			}
		}
		
		
		
		return ans;
	}
	
	
	private static boolean hasPerm(CommandSender s, String perm)
	{
		return ((s instanceof ConsoleCommandSender) || ((Player)s).hasPermission(perm));
	}
}
