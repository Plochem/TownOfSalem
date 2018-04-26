package com.plochem.tos.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.plochem.tos.Main;
import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.game.items.GameItem;

public class InventoryClick implements Listener{
	private Main plugin;
	public InventoryClick(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		GamePlayer gp = plugin.getGamePlayer(p);//TODO prevent taking items out
		if(e.getCurrentItem() != null){
			if(gp == null){ //not in the game
				//shop
				//e.setCancelled(false);	if click shop
			} else { //in the game
				//TODO check if player is in the game like roles already assigned
				e.setCancelled(true);
				String selectedPlayerName = e.getCurrentItem().getItemMeta().getDisplayName();
				if(selectedPlayerName.equals(p.getName())){ //TODO allow to click itself if doctor or vig/vet (whichever has the alerts)
					p.sendMessage("§cYou cannot do that to yourself!");
				} else {
					for(GameItem items : gp.getItems()){
						if(items.getGUI().equals(e.getClickedInventory())){ //check if player is clicking in the GUI designated for its role
							if(gp.getArena().getGame().getEvent().equals("Night")){  //TODO allow for jailors to do their choosing
								p.sendMessage(items.getOnClickMsg() + selectedPlayerName + ".");
								gp.getRole().performAbility(selectedPlayerName, gp.getArena().getGame());
								items.getGUI().getViewers().get(0).closeInventory();
								break;
							} else {
								p.sendMessage("§cYou are not allowed to do this right now!");
								items.getGUI().getViewers().get(0).closeInventory();
							}

						}
					}
				}
			}
		}
		
	}
}

