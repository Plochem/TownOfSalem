package com.plochem.tos.roles;

import com.plochem.tos.game.Game;
import com.plochem.tos.game.GamePlayer;

public interface Ability {
	public void giveItems(GamePlayer gp);
	public void performAbility(String playerName, Game game);
}
