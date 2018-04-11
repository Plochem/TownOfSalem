package com.plochem.tos.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerDataFile implements Listener{ //creates a data file for player
	File playerFile;
	YamlConfiguration playerData;
	@EventHandler
	public void createPlayerFile(PlayerJoinEvent e) throws IOException{
		UUID playerID = e.getPlayer().getUniqueId();
		String playerName = e.getPlayer().getName();
		playerFile = new File("plugins/TownOfSalem/playerdata/" + playerID.toString() + ".yml");
		playerData = YamlConfiguration.loadConfiguration(playerFile);
		if(!(playerFile.exists())) {
			Bukkit.getServer().getLogger().info("[TownofSalem] Creating player data file for " + playerName + "!");
			playerData.createSection("data");
			createStats();
			playerData.save(playerFile);
		}  else {
			Bukkit.getServer().getLogger().info("[TownofSalem] Player data file already exists for " + playerName+ "! Skipping creation...");
		}
	}
	
	private void createStats(){
		playerData.set("data.wins", 0);
		playerData.set("data.losses", 0);
		playerData.set("data.tokens", 0);
		playerData.set("data.premium_tokens", 0);
		playerData.set("data.games_played", 0);
	}
}
