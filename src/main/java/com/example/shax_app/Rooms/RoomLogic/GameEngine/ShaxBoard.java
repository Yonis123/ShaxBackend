package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import java.util.HashMap;

public class ShaxBoard{
	
	private HashMap<String, Spot> board = new HashMap<>();

	public ShaxBoard(){
		
		this.buildBoard();	

	}

	public ShaxBoard(ShaxBoard other){
		this.buildBoard(other);
	}

	private void buildBoard(){
		
		for(int spotNumber = 0; spotNumber < 24; spotNumber++){
			String spotN = String.valueOf(spotNumber);
			Spot spot = new Spot(spotN);
			board.put(spotN, spot);
		}

	}

	private void buildBoard(ShaxBoard other){
		for(int spotNumber = 0; spotNumber < 24; spotNumber++){
			String spotN = String.valueOf(spotNumber);
			Spot spot = new Spot(spotN);
			spot.setOccupiedBy(other.getSpot(spotN).getOccupiedBy());
			board.put(spotN, spot);
		}
	}

	 
	public Spot getSpot(String positionNumber){
		return board.get(positionNumber);
	}

	public HashMap<String, Spot> getBoard(){
		return this.board;
	}

	public void placePiece(String spotNumber, Player player){
		Spot spot = this.board.get(spotNumber);
		spot.setOccupiedBy(player);
		this.board.put(spot.getSpotNumber(), spot);
	}

	public void removePiece(String spotToRemove){
		Spot spot = this.board.get(spotToRemove);
		spot.setOccupiedBy(null);
		this.board.put(spotToRemove, spot);
	}

	public void moveSpot(String fromNum, String toNum, Player player){
		Spot spot = this.board.get(fromNum);
		spot.setOccupiedBy(null);
		this.board.put(fromNum, spot);

		spot = this.board.get(toNum);
		spot.setOccupiedBy(player);
		this.board.put(toNum, spot);
	}


}


