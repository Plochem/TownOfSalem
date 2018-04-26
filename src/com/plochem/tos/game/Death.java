package com.plochem.tos.game;

import com.plochem.tos.roles.Role;

public class Death {
	private String playerKilled;
	private Role killer;
	
	public Death(String playerKilled, Role killer){
		this.playerKilled = playerKilled;
		this.killer = killer;
	}

	public String getPlayerKilled() {
		return playerKilled;
	}

	public void setPlayerKilled(String playerKilled) {
		this.playerKilled = playerKilled;
	}

	public Role getKiller() {
		return killer;
	}

	public void setKiller(Role killer) {
		this.killer = killer;
	}
	
	
}
