package com.plochem.tos.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.plochem.tos.Main;
import com.plochem.tos.game.GamePlayer;

public class InventoryClick implements Listener{
	Main plugin;
	public InventoryClick(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		GamePlayer gp = plugin.getGamePlayer(p);
		if(gp == null){ //not in the game
			//shop
		} else { //in the game
			
		}
	}
}
