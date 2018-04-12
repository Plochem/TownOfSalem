package com.plochem.tos.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.plochem.tos.Arena;
import com.plochem.tos.game.items.GameItem;
import com.plochem.tos.roles.Role;

public class GamePlayer{
	
	private Player player;
	private Role role;
	private Arena arena;
	private boolean hasJoined = false;
	private List<GameItem> gameItems = new ArrayList<>();
	
	public GamePlayer(Player player, Arena arena) {
		this.player = player;
		this.arena = arena;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role){
		this.role = role;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	public boolean getHasJoined() {
		return hasJoined;
	}

	public void setHasJoined(boolean hasJoined) {
		this.hasJoined = hasJoined;
	}
	/**
	 * Gives the player an item
	 * @param item
	 */
	public void give(GameItem item){
		gameItems.add(item);
		player.getInventory().addItem(item.getItemRep());
	}
	
	public void removeItem(GameItem item){
		gameItems.remove(item);
		player.getInventory().remove(item.getItemRep());
	}
	
	public List<GameItem> getItems(){
		return gameItems;
	}
	
	public void leaveGame(){
		arena.getPlayers().remove(this);
		if(arena.getAlivePlayers().contains(this)) arena.getAlivePlayers().remove(this);
		if(arena.getDeadPlayers().contains(this)) arena.getDeadPlayers().remove(this);
	}
	
}
