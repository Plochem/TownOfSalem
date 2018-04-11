package com.plochem.tos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.listeners.InventoryClick;
import com.plochem.tos.listeners.PlayerDataFile;
import com.plochem.tos.listeners.PlayerInteract;

public class Main extends JavaPlugin{
	Main plugin;

	File arenaFile;
	YamlConfiguration arenaData;

	String noArenasCreated = "§6[Town Of Salem] §cNo arenas have been created yet!";
	public List<Arena> listOfArenas = new ArrayList<>();
	Set<String> arenaList;
	final String PREFIX = "§6[Town Of Salem] ";

	public Main() {

	}
	public void onEnable() {
		plugin = this;
		Bukkit.getServer().getLogger().info("[TownofSalem] Starting Up...");
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDataFile(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(this), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(this), plugin);
		arenaFile = new File("plugins/TownOfSalem/arenas.yml");
		arenaData = YamlConfiguration.loadConfiguration(arenaFile);
		if(!(arenaFile.exists())) {
			Bukkit.getServer().getLogger().info("[TownofSalem] Creating arena storage file!");
			arenaData.createSection("Arenas");
			saveConfig();
		}  else {
			Bukkit.getServer().getLogger().info("[TownofSalem] Arena storage file already exists! Skipping creation...");
		}
		arenaList = arenaData.getConfigurationSection("Arenas").getKeys(false); // gets all arena names in file
		loadArenaFromFileToObj(arenaList); // transfers data from arenas.yml to objects for convenience
	}

	public void onDisable() {
	
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Player p = (Player) sender;
		if(command.getName().equalsIgnoreCase("tos") || command.getName().equalsIgnoreCase("townofsalem")){
			arenaList = arenaData.getConfigurationSection("Arenas").getKeys(false); //add to list when you create new arena
			if(args.length == 1){
				//				if(args[0].equals("stats")){ THIS IS HOW YOU GET STATS
				//					File f = new File("plugins/TownOfSalem/playerdata/" + p.getUniqueId().toString() + ".yml");
				//					YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
				//					p.sendMessage(c.getString("data.wins"));
				//				}
				if(args[0].equalsIgnoreCase("help")){
					p.sendMessage("§6----- Town of Salem Command Help -----");
					p.sendMessage("/tos help - §eDisplays this page.");
					p.sendMessage("/tos create [name] - §eCreates a new arena in the current world you are in");
					p.sendMessage("/tos delete [name] - §eDeletes the arena with that name");
					p.sendMessage("/tos deleteall - §eDeletes all arenas");
					p.sendMessage("/tos join [name] - §eJoins an arena with that name");
					p.sendMessage("/tos leave - §eLeaves the current arena you are in");
					p.sendMessage("/tos list - §eDisplays all the created arenas");
					p.sendMessage("/tos setspawn [name] [num] - §eSets the num-th spawnpoint for the arena with that name");		
				}
				if(args[0].equalsIgnoreCase("list")){
					if(listOfArenas.isEmpty()){
						p.sendMessage(noArenasCreated);
						return false;
					}
					p.sendMessage("§eHere is the list of current arenas:");
					for(Arena a : listOfArenas){
						p.sendMessage("§e- §a" + a.getName());
					}
				}
				if(args[0].equalsIgnoreCase("deleteall")){
					if(listOfArenas.isEmpty()){
						p.sendMessage(noArenasCreated);
						return false;
					}
					for(String key : arenaData.getConfigurationSection("Arenas").getKeys(false)){ //sets all arenas in data file to null
						arenaData.set("Arenas." + key, null);
					}
					listOfArenas.clear();
					p.sendMessage(PREFIX + "§aSuccessfully deleted all arenas!");
					saveConfig();
				}
				if(args[0].equalsIgnoreCase("leave")){ //TODO: able to leave arena
					for(Arena arena : listOfArenas){
						for(GamePlayer gp : arena.getPlayers()){
							if(gp.getPlayer().equals(p)){
								//TODO: make to method
							}
						}
					}
				}
			} else if(args.length == 2){
				if(args[0].equalsIgnoreCase("create")){ //TODO: prevent creating arena in same world
					//					if(duplicateWorld()){
					//						p.sendMessage("§6[Town Of Salem] §cAn arena is this world currently exists! ");
					//						return false;
					//					}
					if(arenaExists(args[1])){ // to prevent creating same arena w/ same name
						p.sendMessage(PREFIX + "§cAn arena called \"§e" + args[1] + "§c\" already exists!");
						return false;
					} else {
						if(!StringUtils.isAlphanumeric(args[1])){ //not alpha-numeric
							p.sendMessage(PREFIX + "§cThe arena name must be alphanumeric!");
						} else {
							arenaData.set("Arenas." + args[1] + ".world", p.getWorld().getName());
							arenaData.createSection("Arenas." + args[1] + ".Spawnpoints");
							p.sendMessage(PREFIX + "§aSuccessfully created a new arena called §e" + args[1] + "§a!");
							listOfArenas.add(new Arena(p.getWorld(), args[1], null));
							saveConfig();
						}
					}

				}
				if(args[0].equalsIgnoreCase("delete")){
					if(listOfArenas.isEmpty()){
						p.sendMessage(noArenasCreated);
						return false;
					}
					if(arenaExists(args[1])){
						for(Arena arena : listOfArenas){
							if(arena.getName().equals(args[1])){
								if(arena.getHasStarted()){//prevent deleting an arena that is ongoing
									p.sendMessage(PREFIX + "§cYou may not delete an ongoing game! Please wait for it to end.");
									return false;
								} else {
									listOfArenas.remove(arena);
									break;
								}
							}
						}
						arenaData.set("Arenas." + args[1], null);
						saveConfig();
						p.sendMessage(PREFIX + "§aSuccessfully deleted an arena called \"§e" + args[1] + "§a\"!");
						return true;
					}
					p.sendMessage(PREFIX + "§cAn arena called \"§e" + args[1] + "§c\" does not exist!");
				}
				if(args[0].equalsIgnoreCase("join")){
					if(listOfArenas.isEmpty()){
						p.sendMessage(noArenasCreated);
						return false;
					}
					if(arenaExists(args[1])){ //args[1] is inputed name
						Arena arena = getJoinableArena(args[1]);
						if(arena != null){ //null = game has started
							if(!arena.areSpawnsSet()){
								p.sendMessage(PREFIX + "§cFailed to join this arena. Not all spawnpoints have been configured!");
								return false;
							}
							p.sendMessage(PREFIX + "§aJoining §e" + args[1] + "§a!");
							joinArena(arena, p);
						} else {
							p.sendMessage(PREFIX + "§cThat arena already started! Please join another one.");
						}
					} else {
						p.sendMessage(PREFIX + "§cAn arena called \"§e" + args[1] + "§c\" does not exist!");
					}
				}
			} else if(args.length == 3){
				if(args[0].equalsIgnoreCase("setspawn")){
					if(StringUtils.isNumeric(args[2])){
						int spawnNum = 0;
						try { //prevents an attempt to parse a huge ass number
							spawnNum = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							p.sendMessage(PREFIX + "§cThe number of the specified position should be between 1 and 15 inclusive.");
							return false;
						}
						if(spawnNum > 15 || spawnNum < 1){ //check if num is higher than 15 or less than 1
							p.sendMessage(PREFIX + "§cThe number of the specified position should be between 1 and 15 inclusive.");
						} else { 
							if(arenaExists(args[1])){ //is args[1] a valid arena name
								Arena currArena = getArena(args[1]);									
								if(p.getWorld().equals(currArena.getWorld())){//check if player is in same world as targeted arena
									Location loc = p.getLocation();
									currArena.addSpawnpoint(loc, spawnNum);
									arenaData.set("Arenas." + args[1] + ".Spawnpoints." + spawnNum, loc);
									p.sendMessage(PREFIX + "§aYou have created a new spawnpoint §e(#" + spawnNum + ")§a at §e" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + "§a!");
									saveConfig();
								} else {
									p.sendMessage(PREFIX + "§cYou must be in the same world as the arena you want to set a spawn.");
								}

							} else {
								p.sendMessage(PREFIX + "§cAn arena called \"§e" + args[1] + "§c\" does not exist!");
							}
						}
					} else {
						p.sendMessage(PREFIX + "§cEnter a valid number to specify a spawnpoint.");
					}
				}
			}

			return true;
		}
		return false;
	}

	//	private boolean duplicateWorld() {
	//		if()
	//		return false;
	//	}
	private void loadArenaFromFileToObj(Set<String>arenaList){
		for(String mapName : arenaList){
			String worldName = arenaData.getString("Arenas." + mapName + ".world");
			Location[] spawnpoints = getLocsFromFile(mapName, worldName);
			World world = Bukkit.getServer().getWorld(worldName);
			listOfArenas.add(new Arena(world, mapName, spawnpoints));
		}
	}
	
	private Location[] getLocsFromFile(String mapName, String worldName){
		Location[] spawnpoints = new Location[15];
		Set<String> locNums = arenaData.getConfigurationSection("Arenas." + mapName + ".Spawnpoints").getKeys(false); // the spawnpoint numbers 1-15
		for(int i = 0; i < locNums.size(); i++){
			String spawnID = Integer.toString(i+1);
			spawnpoints[i] = (Location) arenaData.get("Arenas." + mapName + ".Spawnpoints." + spawnID);
		}
		return spawnpoints;
	}
	
	public void saveConfig(){
		try{
			arenaData.save(arenaFile);
		} catch(Exception e){ 
			e.printStackTrace();
			Bukkit.getServer().getConsoleSender().sendMessage("[TownofSalem] §c[Error] Arena.yml failed to be saved!");
		}
	}

	private boolean arenaExists(String arenaName){
		for(Arena arena : listOfArenas){
			if(arena.getName().equals(arenaName)){
				return true;
			}
		}
		return false;
	}
	
	private Arena getArena(String arenaName){
		for(Arena arena : listOfArenas){
			if(arena.getName().equals(arenaName)){
				return arena;
			}
		}
		return null;
	}

	private Arena getJoinableArena(String arenaName){
		for(Arena arena : listOfArenas){
			if(arena.getName().equals(arenaName) && !arena.getHasStarted()){
				return arena;
			}
		}
		return null;
	}

	private void joinArena(Arena arena, Player p){ //TODO: dont allow joining multiple arenas
		if(arena.getPlayers().size() < 15){
			arena.addPlayer(new GamePlayer(p, arena));
			arena.sendMessage(p.getName() + "§e has joined (" + "§b" + arena.getPlayers().size() + "§e/" + "§b15§e)!");
			if(arena.getPlayers().size() == 15){
				arena.setHasStarted(true);
				arena.start(plugin);
			}
		} else {
			p.sendMessage(PREFIX + "§cThis arena is currently full! Please join another one.");
		}
	}
	
	public GamePlayer getGamePlayer(Player p){
		for(Arena arena : plugin.listOfArenas){
			for(GamePlayer gp : arena.getAlivePlayers()){
				if(gp.getPlayer().equals(p)){
					return gp;
				}
			}
		}
		return null;
	}
}
