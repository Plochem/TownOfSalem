package com.plochem.tos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.plochem.tos.game.Game;
import com.plochem.tos.game.GamePlayer;

public class Arena {
	Plugin plugin;
	List<GamePlayer> alivePlayers = new ArrayList<>(); // only alive
	List<GamePlayer> players = new ArrayList<GamePlayer>(); //only all
	List<GamePlayer> deadPlayers = new ArrayList<GamePlayer>(); //only dead
	Location[] spawnpoints = new Location[15];
	World world;
	String name;
	boolean hasStarted;

	Countdown cd;

	public Arena(World world, String name, Location[] spawnpoints) {
		this.world = world;
		this.name = name;
		if (spawnpoints != null) {
			this.spawnpoints = spawnpoints;
		}

	}

	public List<GamePlayer> getPlayers() {
		return players;
	}

	public void addPlayer(GamePlayer gp) {
		players.add(gp);
		alivePlayers.add(gp);
	}

	public List<GamePlayer> getDeadPlayers() {
		return deadPlayers;
	}

	public void addDeadPlayer(GamePlayer gp) {
		deadPlayers.add(gp);
	}

	public List<GamePlayer> getAlivePlayers() {
		return alivePlayers;
	}

	public void addAlivePlayer(GamePlayer gp) {
		alivePlayers.add(gp);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getHasStarted() {
		return hasStarted;
	}

	public void setHasStarted(boolean hasStarted) {
		this.hasStarted = hasStarted;
	}

	public Location[] getSpawnpoints() {
		return spawnpoints;
	}

	public void addSpawnpoint(Location loc, int pos) {
		spawnpoints[pos-1] = loc;
	}

	public void sendMessage(String message){
		for(Player p : world.getPlayers()){
			p.sendMessage(message);
		}
	}

	public void start(Main plugin) {
		this.plugin = plugin;
		cd = new Countdown(10);
		cd.doCountdown(plugin, this);
	}

	public boolean areSpawnsSet() {
		for(int i = 0; i < spawnpoints.length; i++){
			if(spawnpoints[i] == null){
				return false;
			}
		}
		return true;
	}

	public void startGame() {
		Game game = new Game(this, plugin);
		game.assignRoles();
	}


	//	public static void removeScoreBoard(Player p) {
	//		objective.unregister();
	//		p.setScoreboard(manager.getNewScoreboard());
	//	}

}
