package com.plochem.tos.roles.mafia;

import com.plochem.tos.game.GamePlayer;
import com.plochem.tos.game.items.GameItem;
import com.plochem.tos.game.items.MafiaChooser;
import com.plochem.tos.roles.Ability;
import com.plochem.tos.roles.Role;

public class Mafiaso extends Role implements Ability{
	GameItem mafiaChooser = new MafiaChooser();
	public Mafiaso(){
		this.setName("Mafiaso");
		this.setColoredName("§4§lMafiaso");
		this.setAlignment("Mafia");
		this.setAtkVal(1);
		this.setDefVal(0);
		this.setCanPerformNightAbility(true);
	}

	@Override
	public void performAbility(GamePlayer gp) {
		mafiaChooser.update(gp.getArena()); //updates the player list
		gp.give(mafiaChooser);
	}


}
