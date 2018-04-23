package com.plochem.tos.roles;

import com.plochem.tos.game.Game;
import com.plochem.tos.game.GamePlayer;

public class Role{
	String goal;
	String name;
	String coloredName;
	String attributes;
	String alignment;
	boolean canPerformNightAbility;
	int atkVal = 0;
	int defVal = 0;
	

//		if(role.equals("Framer") || role.equals("Godfather") || role.equals("Mafiaso")){
//			// Mafia roles
//			setName("§4§l" + role);
//			if(role.equals("Framer")){
//				//Framer
//				setAtkVal(0);
//				setDefVal(0);
//			} else if(role.equals("Mafiaso")){
//				//Mafiaso
//				setAtkVal(1);
//				setDefVal(0);
//			} else{
//				//Godfather
//				setAtkVal(1);
//				setDefVal(1);
//			}
//			setAlignment("§4Mafia");
//		} else if(role.equals("Executioner") || role.equals("Jester") || role.equals("Serial Killer")){
//			// Neutral roles
//			setName("§7§l" + role);
//			if(role.equals("Jester")){
//				//Jester
//				setAtkVal(0);
//				setDefVal(0);
//			} else if(role.equals("Executioner")){
//				//Executioner
//				setAtkVal(0);
//				setDefVal(1);
//			} else {
//				// Serial Killer
//				setAtkVal(1);
//				setDefVal(1);
//			}
//			setAlignment("§7Neutral");
//		} else {
//			// Town roles
//			setName("§a§l" + role);
//			if(role.equals("Jailor")){
//				setAtkVal(3);
//				setDefVal(0);
//			} else if(role.equals("Veteran")){
//				setAtkVal(2);
//				setDefVal(0);
//			} else if(role.equals("Vigilante")){
//				setAtkVal(1);
//				setDefVal(0);
//			} else if(role.equals("Bodyguard")){
//				setAtkVal(2);
//				setDefVal(0);
//			} else {
//				setAtkVal(0);
//				setDefVal(0);
//			}
//			setAlignment("§aTown");
//		}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColoredName() {
		return coloredName;
	}

	public void setColoredName(String coloredName) {
		this.coloredName = coloredName;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	
	public int getAtkVal() {
		return atkVal;
	}

	public void setAtkVal(int atkVal) {
		this.atkVal = atkVal;
	}

	public int getDefVal() {
		return defVal;
	}

	public void setDefVal(int defVal) {
		this.defVal = defVal;
	}

	public boolean canPerformNightAbility() {
		return canPerformNightAbility;
	}

	public void setCanPerformNightAbility(boolean canPerformNightAbility) {
		this.canPerformNightAbility = canPerformNightAbility;
	}

	public void performAbility(String playerName, Game game) {
	}

	public void giveItems(GamePlayer gp) {
	}

	public void givePlayerList(GamePlayer gp) {
		//TODO build player list
	}
	
	

}
