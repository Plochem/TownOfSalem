package com.plochem.tos.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.plochem.tos.Main;
import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.game.items.GameItem;
import com.plochem.tos.game.items.MafiaChooser;
import com.plochem.tos.roles.mafia.Mafiaso;

public class PlayerInteract implements Listener{
	Main plugin; 
	public PlayerInteract(Main main) {
		plugin = main;
	}
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.AIR) return;
		GamePlayer gp = plugin.getGamePlayer(p);
		if(gp == null){ //not in the game
			//TODO the shop
		} else { //in the game
			if(gp.getRole() instanceof Mafiaso && e.getMaterial() == Material.IRON_SWORD){
				for(GameItem item : gp.getItems()){
					if(item instanceof MafiaChooser){
						p.openInventory(item.getGUI());
					}
				}
			}
			
		}

		//TODO find the corresposnding gameplayer ject, get that player's items. check what item is it. open whatever it needs
	}
}
