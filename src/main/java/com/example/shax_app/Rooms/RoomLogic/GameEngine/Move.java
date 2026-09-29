package com.example.shax_app.Rooms.RoomLogic.GameEngine;


public abstract class Move {

	private final Player player;
	private final GamePhase phase;
	private Boolean needsHelp = false;;

	public Move(Player player, GamePhase phase){
	
		this.player = player;
		this.phase = phase;
		
	}
	
	public Move(Player player, GamePhase phase, Boolean needsHelp){
	
		this.player = player;
		this.phase = phase;
		this.needsHelp = needsHelp;
		
	}


	public Player getPlayer(){
		return this.player;
	}

	public GamePhase getPhase(){
		return this.phase;
	}

	public Boolean needsHelp(){
		return this.needsHelp;
	}

	

}  
