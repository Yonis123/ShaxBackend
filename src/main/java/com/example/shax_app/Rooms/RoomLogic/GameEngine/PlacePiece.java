package com.example.shax_app.Rooms.RoomLogic.GameEngine;

public class PlacePiece extends Move{
	private String spotNumber;

	public PlacePiece(Player player, GamePhase phase, String spotNumber){
		super(player, phase);		
		this.spotNumber = spotNumber;
	}

	public String getSpotNumber(){
		return this.spotNumber;
	}
}  
