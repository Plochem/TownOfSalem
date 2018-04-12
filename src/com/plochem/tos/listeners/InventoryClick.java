package com.plochem.tos.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.plochem.tos.Main;
import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.game.items.GameItem;

public class InventoryClick implements Listener{
	Main plugin;
	public InventoryClick(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		GamePlayer gp = plugin.getGamePlayer(p);//TODO prevent taking items out
		if(gp == null){ //not in the game
			//shop
			//e.setCancelled(false);	if click shop
		} else { //in the game
			//TODO check if player is in the game like roles already assigned
			e.setCancelled(true);
			String selectedPlayerName = e.getCurrentItem().getItemMeta().getDisplayName();
			for(GameItem items : gp.getItems()){
				if(items.getGUI().equals(e.getClickedInventory())){
					p.sendMessage(items.getOnClickMsg() + selectedPlayerName);
					break;
				}
			}
		}
	}
}

