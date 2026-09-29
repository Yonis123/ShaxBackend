package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import java.util.ArrayList;

class MovesGenerator{

	
	public static ArrayList<Move> generatePossibleMoves(ShaxBoard board, Player currentPlayer, Player notCurrentPlayer, GamePhase phase){
		// how do i generate all the possible moves?
		// if its in the placing phase, then just loop through all the spots, and return 
		// the spots that are empty as possible moves
		// if its in the moving phase then loop through everything, look for an empty spot, if i find an empty spot
		// check if any of its neighbors match the current player, and add that to the list of potential moves
		// if its in removal phase, search and look for any pieces then its pretty much searching for all the spots that the opponent has pieces and those are valid piece to remove
		// how would i ensure that 

		ArrayList<Move> moves = new ArrayList<>();
		
		if (phase == GamePhase.PLACING){

			for (int i = 0; i < 24; i++){
				String spotNumber = String.valueOf(i);
				Spot spot = board.getBoard().get(spotNumber);
				if (spot.getOccupiedBy() == null){
					Move move = new PlacePiece(currentPlayer, phase, spot.getSpotNumber());
					moves.add(move);
				}
			}

		} else if (phase == GamePhase.REMOVING){

			for (int i = 0; i < 24; i++){
				String spotNumber = String.valueOf(i);
				Spot spot = board.getBoard().get(spotNumber);
				if (spot.getOccupiedBy() == notCurrentPlayer){
					Move move = new RemovePiece(currentPlayer, phase, spot.getSpotNumber());
					moves.add(move);
				}

				
			}

		} else {
			
			for (int i = 0; i < 24; i++){
				String spotNumber = String.valueOf(i);
				Spot spot = board.getBoard().get(spotNumber);
				// check neighbors for this spot
				if (spot.getOccupiedBy() == currentPlayer){
					int[] neighbors = BoardUtils.getNeighbors(spot.getSpotNumber());
					// add any empty neighbor to move
					for (int neighbor : neighbors){
						String neighborString = String.valueOf(neighbor);
						if (board.getBoard().get(neighborString).getOccupiedBy() == null){
							Move move = new MoveSpot(currentPlayer, phase, spot.getSpotNumber(), neighborString);
							moves.add(move);
						}
					}
				}


			}

		}

		return moves;


	}


}
