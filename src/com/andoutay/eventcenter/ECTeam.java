package com.andoutay.eventcenter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

public class ECTeam
{
	public Inventory defaultLoadout;
	public Location spawnPoint;
	private String name;
	private ChatColor chatColor;
	
	public ECTeam() {
		defaultLoadout = null;
	}
	
	public void setChatColor(ChatColor ctColor) {
		chatColor = ctColor;
	}
	
	public void setChatColor(String color) {
		//remember that this just takes the character (0-f, k-o) and not any special characters
		chatColor = ChatColor.getByChar(color);
	}
	
	public ChatColor chatColor() {
		return chatColor;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String name() {
		if (name == null) {
			name = "";
		}
		return name;
	}
}
