package com.plochem.tos.roles.mafia;

import com.plochem.tos.game.Death;
import com.plochem.tos.game.Game;
import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.game.items.GameItem;
import com.plochem.tos.game.items.MafiaChooser;
import com.plochem.tos.roles.Ability;
import com.plochem.tos.roles.Role;

public class Mafiaso extends Role implements Ability{
	private GameItem mafiaChooser = new MafiaChooser();
	public Mafiaso(){
		this.setName("Mafiaso");
		this.setColoredName("§4§lMafiaso");
		this.setAlignment("Mafia");
		this.setAtkVal(1);
		this.setDefVal(0);
		this.setCanPerformNightAbility(true);
	}

	@Override
	public void giveItems(GamePlayer gp) {
		mafiaChooser.update(gp.getArena()); //updates the player list
		gp.give(mafiaChooser);
	}

	@Override
	public void performAbility(String playerName, Game game) {
		game.addNewDeath(new Death(playerName, this));
		//TODO change method
	}

}
