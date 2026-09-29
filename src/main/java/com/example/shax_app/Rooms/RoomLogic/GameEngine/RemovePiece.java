package com.example.shax_app.Rooms.RoomLogic.GameEngine;



public class RemovePiece extends Move {

	private String spotToRemove;
	
	public RemovePiece(Player player, GamePhase phase, String spotToRemove){
		super(player, phase);
		this.spotToRemove = spotToRemove;
	}

	public String getSpotToRemove(){
		return this.spotToRemove;
	}


}
