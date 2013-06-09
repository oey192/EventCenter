package com.andoutay.eventcenter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ECEventHandler implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evt)
	{
		//if player is in a chat room, cancel the chat event and do a player.sendMessage to everyone in that group
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
