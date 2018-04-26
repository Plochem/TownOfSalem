package com.plochem.tos.game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.plochem.tos.Arena;
import com.plochem.tos.Countdown;
import com.plochem.tos.api.HoloAPI;
import com.plochem.tos.roles.Role;
import com.plochem.tos.roles.mafia.Framer;
import com.plochem.tos.roles.mafia.Godfather;
import com.plochem.tos.roles.mafia.Mafiaso;

public class Game {
	private Plugin plugin;
	private Arena arena;
	private Role[] roles = {new Mafiaso(), new Framer(), new Godfather()};
	@SuppressWarnings("unused")
	private List<Role> copyOfRoles;
	private String event;
	private Countdown cd;
	private List<Death> recentDeaths = new ArrayList<>();
	
	String mafiaKill;
	
	public Game(Arena arena, Plugin plugin){
		this.arena = arena;
		this.plugin = plugin;
	}
	public Arena getArena(){
		return arena;
	}
	public String getEvent(){
		return event;
	}
	public void addNewDeath(Death death){//TODO change to add new recent death to list. 
		recentDeaths.add(death);
	}
	public void nextEvent(){
		if(event.equals("Day")){
			night();
		} else if(event.equals("Night")){
			goThroughDeaths();
		} else if(event.equals("Transition")){
			discussion();
		} else if(event.equals("Discussion")){
			vote();
		}
	}
	public void assignRoles(){
		copyOfRoles = new ArrayList<>(Arrays.asList(roles));
		for(int i = 0; i < arena.getAlivePlayers().size(); i++){
			arena.getAlivePlayers().get(i).getPlayer().teleport(arena.getSpawnpoints()[i]);
			//arena.getAlivePlayers().get(i).setRole(pickRandom());
			arena.getAlivePlayers().get(i).setRole(roles[0]); //TODO delete this one. only for testing
			arena.getAlivePlayers().get(i).getPlayer().sendMessage("You are a: " + arena.getAlivePlayers().get(i).getRole().getName());
		}
		day1();
	}
	
	private void day1(){
		arena.getWorld().setTime(6000);			
		event = "Day";
		cd = new Countdown(15);
		cd.start(plugin, this);
	}
	
	private void night(){
		arena.getWorld().setTime(18000);
		event = "Night";
		for(GamePlayer gp : arena.getAlivePlayers()){
			if(gp.getRole().canPerformNightAbility()){
				gp.getRole().giveItems(gp);
				System.out.println(gp.getPlayer().getName() + ": " + gp.getRole().getName());
			}
		}
		cd = new Countdown(30);
		cd.start(plugin, this);
	}
	
	@SuppressWarnings("deprecation")
	private void goThroughDeaths(){
		//TODO update playerlist (the book)
		for(GamePlayer gp : arena.getAlivePlayers()){ //clear inventory
			gp.getPlayer().getInventory().clear();
			gp.getItems().clear();
		}
		arena.getWorld().setTime(6000);
		event = "Transition";
		displayBoard(0);
		for(Death death : recentDeaths){
			Player killed = Bukkit.getPlayer(death.getPlayerKilled());
			if(killed != null){
				killed.sendTitle("§cYou Died!", "");	
			}
			GamePlayer target = getGamePlayer(killed);
			arena.removeAlivePlayer(target);
			arena.addDeadPlayer(target);
		}
		cd = new Countdown(18);
		cd.showDeaths(plugin, this, recentDeaths, 0);
	}
	
	private void discussion(){
		recentDeaths.clear();
		arena.getWorld().setTime(6000);
		event = "Discussion";
		cd = new Countdown(45);
		cd.start(plugin, this);
	}
	
	private void vote(){ //TODO add voting things
		event = "Voting";
		cd = new Countdown(30);
		cd.start(plugin, this);
		for(GamePlayer gp: arena.getAlivePlayers()){
//			gp.getRole().givePlayerList(gp);
//			gp.getPlayer().getName() + " - " + "place";

		}
	}
	
//	private Role pickRandom() {
//		Random randomizer = new Random();
//		if(copyOfRoles.isEmpty()) return null;
//		int randNum = randomizer.nextInt(copyOfRoles.size());
//		Role randRole = copyOfRoles.get(randNum);
//		copyOfRoles.remove(randNum); // prevents picking same role more than once
//		return randRole;
//	}

	public void displayBoard(int time){
		for(GamePlayer gp : arena.getPlayers()){
			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			Scoreboard roleBoard =  Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objective = roleBoard.registerNewObjective("Role", "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(gp.getRole().getColoredName()); // displays player's role at top of scoreboard
			objective.getScore("§7Town of Salem - " + df.format(new Date())).setScore(10); // the date
			objective.getScore(event + ": " + time).setScore(9);
			objective.getScore("").setScore(8); // empty line
			objective.getScore("§eAttack: §3§n" + gp.getRole().getAtkVal() + "§r     §eDefense: §3§n" + gp.getRole().getDefVal()).setScore(7); // attack and defense value
			objective.getScore("§eAlignment:§r " + gp.getRole().getAlignment()).setScore(6);;
			//TODO:display attributes
			//TODO:display goals
			gp.getPlayer().setScoreboard(roleBoard);
		}
	}
	
	private GamePlayer getGamePlayer(Player killed){
		for(GamePlayer gp : arena.getAlivePlayers()){
			if(gp.getPlayer().equals(killed)){
				return gp;
			}
		}
		return null;
	}
}
