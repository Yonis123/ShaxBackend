package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import com.example.shax_app.Rooms.RoomLogic.GameEngine.Player;

public class Spot {
		
	private String spotNumber;
	private Player occupiedBy = null;

	public Spot(String spotNumber){
		this.spotNumber = spotNumber;
	}

	public String getSpotNumber(){
		return this.spotNumber;
	}

	public Player getOccupiedBy(){
		return this.occupiedBy;
	}

	public void setOccupiedBy(Player player){
		this.occupiedBy = player;
	}

	public void removeOccupiedBy(){
		this.occupiedBy = null;
	}

}
