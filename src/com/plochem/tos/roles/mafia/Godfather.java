package com.plochem.tos.roles.mafia;

import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.roles.Ability;
import com.plochem.tos.roles.Role;

public class Godfather extends Role implements Ability{
	public Godfather(){
		this.setName("Godfather");
		this.setColoredName("§4§lGodfather");
		this.setAlignment("Mafia");
		this.setAtkVal(1);
		this.setDefVal(1);
		this.setCanPerformNightAbility(true);
	}

	@Override
	public void givePlayerList(GamePlayer gp) {
		// TODO Auto-generated method stub
		
	}
	
	

}
