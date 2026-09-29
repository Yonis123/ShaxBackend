package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import java.util.ArrayList;

class Referee{

	public static ValidationResults isValid(Move move, ShaxBoard board, Player currentPlayer, Player notCurrentPlayer, GamePhase currentPhase, boolean isHelping){

		if (move.getPlayer() != currentPlayer){
			return ValidationResults.invalid(TheReason.NOT_YOUR_TURN);
		}

		
		if (move.getPhase() != currentPhase){
			return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
		}

		if (move instanceof AskForHelp){
			
		//ensure that there are no other options for the current player
			//if no other options then return validation ok, and then in my make move method i will take this into account
			//then i will change phase to helping, where it is the same as move but the make move method will not allow them to score if they make a mill with their move
	
			
			ArrayList<Move> possibleMoves = MovesGenerator.generatePossibleMoves(board, currentPlayer, notCurrentPlayer, currentPhase);
			if (!possibleMoves.isEmpty()){
				return ValidationResults.invalid(TheReason.MOVES_AVAILABLE);
			}

			return ValidationResults.ok();
			

		}
		
		

		if (currentPhase == GamePhase.PLACING){
			// make sure that the place they are putting is not occupied 
			// make sure that the player is either placing their 12 piece or lower 
			
			if (!(move instanceof PlacePiece placePiece)){
				return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
			}
			int spotNumber;
			try{

			spotNumber = Integer.parseInt(placePiece.getSpotNumber());

			}catch(NumberFormatException e){
				return ValidationResults.invalid(TheReason.INVALID_SPOT);
			}


			if ( spotNumber < 0 || spotNumber > 23 ||  board.getSpot(placePiece.getSpotNumber()).getOccupiedBy() != null){
				return ValidationResults.invalid(TheReason.INVALID_SPOT);
			}

			if (placePiece.getPlayer().getPiecesOnBoard() > 11){
				return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
			}

			

		}else if (currentPhase == GamePhase.MOVING || currentPhase == GamePhase.HELPING){
			// move must be an instance of moveSpot 
			// the from spot is occupied by the current player
			// to spot is empty 
			// is a neightbor of from
			// from and to are valid numbers 
			//
					
			if (!(move instanceof MoveSpot moveSpot)){
				return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
			}
		
			try{

				int spotFrom = Integer.parseInt(moveSpot.getFrom());
				int spotTo = Integer.parseInt(moveSpot.getTo());

				if (spotFrom > 23 || spotFrom < 0 || spotTo > 23 || spotTo < 0){
					ValidationResults.invalid(TheReason.INVALID_SPOT);
				
					}


			}catch(NumberFormatException e){
				return ValidationResults.invalid(TheReason.INVALID_SPOT);
			}

			Spot fromSpot = board.getSpot(moveSpot.getFrom());
			Spot toSpot = board.getSpot(moveSpot.getTo());

			// to spot must be null
			// from must be current player 
			// they must be neighbors 
			// if ask for help true, then there must be a spot freed for the opponent
			ShaxBoard hypothetical = new ShaxBoard(board);
			hypothetical.moveSpot(moveSpot.getFrom(), moveSpot.getTo(), moveSpot.getPlayer());

			 ArrayList<Move> moves = MovesGenerator.generatePossibleMoves(hypothetical, notCurrentPlayer, currentPlayer, currentPhase);

			if(isHelping && moves.isEmpty()){
				return ValidationResults.invalid(TheReason.MOVE_DID_NOT_FREE_OPP);
			}

			if (toSpot.getOccupiedBy() != null){
				return ValidationResults.invalid(TheReason.MOVING_TO_OCCUPIED_SPOT);
			}

			if (fromSpot.getOccupiedBy() == null || fromSpot.getOccupiedBy() == notCurrentPlayer){
				return ValidationResults.invalid(TheReason.FROM_PIECE_NOT_CURRENT_PLAYER);
			}


			if (! (BoardUtils.isNeighbor(moveSpot.getFrom(), moveSpot.getTo()))){
				return ValidationResults.invalid(TheReason.NOT_NEIGHBOR);
			}




			

			

		} else if (currentPhase == GamePhase.REMOVING){
			
			if (!(move instanceof RemovePiece removePiece)){
				return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
			}

			try{

					int removeSpot = Integer.parseInt(removePiece.getSpotToRemove());
					if (removeSpot > 23 || removeSpot < 0){
						ValidationResults.invalid(TheReason.INVALID_SPOT);
					
						}


				}catch(NumberFormatException e){
					return ValidationResults.invalid(TheReason.INVALID_SPOT);
			}

			if (board.getSpot(removePiece.getSpotToRemove()).getOccupiedBy() != notCurrentPlayer){
				return ValidationResults.invalid(TheReason.INVALID_SPOT);
			}




		} else {
			return ValidationResults.invalid(TheReason.INCORRECT_PHASE);
		}
 		
		return ValidationResults.ok();
	}
}
