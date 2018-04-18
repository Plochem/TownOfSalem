package com.plochem.tos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.plochem.tos.game.Death;
import com.plochem.tos.game.Game;
import com.plochem.tos.roles.mafia.Godfather;
import com.plochem.tos.roles.mafia.Mafiaso;

public class Countdown {
	int time;
	int countdown;
	String type;

	public Countdown(int duration){
		time = duration;
	}
	
	public void doCountdown(Plugin plugin, Arena arena){ // does countdown before game starts
		type = "A";
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		countdown = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(time == 0) {
					arena.sendMessage("§eThe game has begun! Assigning roles!");
					Bukkit.getScheduler().cancelTask(countdown); // stops countdown
					arena.startGame();		
					type = "B";
					return;
				}
				if(time!=1){
					arena.sendMessage("§eGame starts in " + "§c" + time + "§e seconds!");
				} else{
					arena.sendMessage("§eGame starts in " + "§c" + time + "§e second!");
				}
				time--;
			}
		}, 0L, 20L);
	}
	
	public void start(Plugin plugin, Game game){ //for all cycles in events during game
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		 countdown = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				game.displayBoard(time);
				if(time == 0){
					Bukkit.getScheduler().cancelTask(countdown);
					game.nextEvent();
				}
				time--;
			}
		}, 0L, 20L);
	}
	/**
	 * 
	 * @param plugin - the current plugin
	 * @param game - the current game
	 * @param recentDeaths - the deaths to loop through
	 * @param index - the index to get in the recentDeaths list
	 */
	public void showDeaths(Plugin plugin, Game game, List<Death> recentDeaths, int index){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		 countdown = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				Death curr = recentDeaths.get(index);
				if(time == 18){
					game.getArena().sendMessage(curr.getPlayerKilled() + " was found dead last night.");
				}
				if(time == 14){
					if(curr.getKiller() instanceof Mafiaso || curr.getKiller() instanceof Godfather){
						game.getArena().sendMessage("He was stabbed by a Mafia member.");
					}
					if(curr.getKiller() == null){ // the player left the game
						game.getArena().sendMessage("He apparently committed suicide");
					}
				}
				if(time == 10){//TODO death notes & will
					game.getArena().sendMessage("display will here");
				}
				if(time == 5){
					game.getArena().sendMessage("display death note");
				}
				//TODO finish the showing deaths
				if(time == 0){
					int i = index;
					i++;
					Bukkit.getScheduler().cancelTask(countdown);
					if(i < recentDeaths.size() - 1){
						showDeaths(plugin, game, recentDeaths, i);
					} else {
						game.nextEvent();
					}
				}
				time--;
			}
		}, 0L, 20L);
	}
	
	public String getType(){
		return type;
	}

	public void stopGameTimer() {
		Bukkit.getScheduler().cancelTask(countdown); // stops countdown
	}

}
