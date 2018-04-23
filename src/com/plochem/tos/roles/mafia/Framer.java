package com.plochem.tos.roles.mafia;

import com.plochem.tos.game.Game;
import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.roles.Ability;
import com.plochem.tos.roles.Role;

public class Framer extends Role implements Ability{
	public Framer(){
		this.setName("Framer");
		this.setColoredName("§4§lFramer");
		this.setAlignment("Mafia");
		this.setAtkVal(0);
		this.setDefVal(0);
		this.setCanPerformNightAbility(true);
	}
	
	public void giveItems(GamePlayer gp) {
//		mafiaChooser.update(gp.getArena()); //updates the player list
//		gp.give(mafiaChooser);
	}

	@Override
	public void performAbility(String playerName, Game game) {
		
		//TODO frame
	}

	@Override
	public void givePlayerList(GamePlayer gp) {
		// TODO Auto-generated method stub
		
	}
}
