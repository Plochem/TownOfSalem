package com.plochem.tos.game.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MafiaChooser extends GameItem{

	public MafiaChooser(){
		this.setItemRep(new ItemStack(Material.IRON_SWORD));
		this.setNameOfItem("§cChoose a player to kill!");
		this.setOnClickMsg("§eYou decided to kill ");
		this.setMenuName("Choose a player to kill!");
	}
}
