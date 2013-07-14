package com.andoutay.eventcenter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ECEventHandler implements Listener
{
	//For most events, check if player is in ECConfig.worlds first. If they're not, ignore them
	//this should help reduce this plugin's impact on the server
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evt)
	{
		//if player is in an event world
		//if player is in an event, cancel the chat event to the rest of the server and do a player.sendMessage to everyone on the player's team OR to entire event if player is not on a team
		//color code message appropriately
		
		//otherwise leave message alone
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		
		ChatColor[] temp = ChatColor.values();
		
		for (int i = 0; i < temp.length; i++)
		{
			p.sendMessage("" + temp[i] + "Chat color: " + i);
		}
		
		//if appropriate permissions, send player messages about upcoming and current events
	}
}
