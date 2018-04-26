package com.plochem.tos.game.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.plochem.tos.Arena;
import com.plochem.tos.game.GamePlayer;

public class GameItem{
	private String onClickMsg;
	private ItemStack itemRep;
	private ItemMeta itemRepMeta;
	private String nameOfItem;
	private String menuName;
	private Inventory GUI;
	private ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM);
	private SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();
	
	/**
	 * Updates the player list in the specified arena
	 * @param arena
	 */
	public void update(Arena arena) {
		List<GamePlayer> alivePlayers = arena.getAlivePlayers();
		for(int i = 0; i < alivePlayers.size(); i++){
			playerSkull.setDurability((short) 3);
			playerSkullMeta.setOwner(alivePlayers.get(i).getPlayer().getName());
			playerSkullMeta.setDisplayName(alivePlayers.get(i).getPlayer().getName());
			playerSkull.setItemMeta(playerSkullMeta);
			GUI.setItem(i, playerSkull);	
		}
		
	}
	
	public String getOnClickMsg() {
		return onClickMsg;
	}


	public void setOnClickMsg(String onClickMsg) {
		this.onClickMsg = onClickMsg;
	}


	public ItemStack getItemRep() {
		return itemRep;
	}


	public void setItemRep(ItemStack itemRep) {
		this.itemRep = itemRep;
	}

	public String getNameOfItem() {
		return nameOfItem;
	}


	public void setNameOfItem(String nameOfItem) {
		this.nameOfItem = nameOfItem;
		itemRepMeta = itemRep.getItemMeta();
		itemRepMeta.setDisplayName(nameOfItem);
		itemRep.setItemMeta(itemRepMeta);
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		GUI = Bukkit.createInventory(null, 18, menuName);
		this.menuName = menuName;
	}

	public Inventory getGUI() {
		return GUI;
	}

}
