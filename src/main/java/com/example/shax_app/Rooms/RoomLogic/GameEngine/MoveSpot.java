package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import com.example.shax_app.Rooms.RoomLogic.GameEngine.Move;

public class MoveSpot extends Move {
	
	private String fromNumber;
	private String toNumber;

	public MoveSpot(Player player, GamePhase phase, String from, String to){
		super(player, phase);
		this.fromNumber = from;
		this.toNumber = to;
	}

	public String getFrom(){
		return this.fromNumber;
	}

	public String getTo(){
		return this.toNumber;
	}


}
