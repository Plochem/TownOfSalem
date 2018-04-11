package com.plochem.tos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.plochem.tos.game.Game;

public class Countdown {
	int time;
	int countdown;

	public Countdown(int duration){
		time = duration;
	}
	
	public void doCountdown(Plugin plugin, Arena arena){ // does countdown before game starts
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		countdown = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(time == 0) {
					arena.sendMessage("§eThe game has begun! Assigning roles!");
					Bukkit.getScheduler().cancelTask(countdown); // stops countdown
					arena.startGame();
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
					//TODO: check what event to go to. eg: if day/discussion, go to night. if night, go to day, etc..
				}
				time--;
			}
		}, 0L, 20L);
	}
}
