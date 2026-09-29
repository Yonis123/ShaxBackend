package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import java.util.HashMap;

class GameState{
	
	// current turn 
	// current phase 
	// current board
	private HashMap<String, Spot> board;
	private GamePhase phase;
	private Player currentPlayer;
	private Player winner;


	public GameState(HashMap<String, Spot> board, GamePhase phase, Player currentPlayer){
		this.board = board;
		this.phase = phase;
		this.currentPlayer = currentPlayer;
	}


	public HashMap<String, Spot> getBoard(){
		return this.board;
	}

	public GamePhase getPhase(){
		return this.phase;
	}

	public Player getPlayer(){
		return this.currentPlayer;
	}

	public void setWinner(Player winner){
		this.winner = winner;
	}


}



