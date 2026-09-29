package com.example.shax_app.Rooms.RoomLogic.GameEngine;

// this GameModel class will be responsible for turn management, game status, and high level coordination 
//
//
public class GameModel{
	
	private ShaxBoard shaxBoard;
	private Player[] players = new Player[2];
	private int currentIndex = 0;
	private GamePhase currentPhase;
	private int firstMill = 1;
	private boolean firstMillMade = false;
	private Player winner = null;
	private boolean askedForHelp = false;

	public GameModel(ShaxBoard shaxBoard, Player player1, Player player2){
		this.shaxBoard = shaxBoard;
		this.players[0] = player1;
		this.players[1] = player2;
		this.currentPhase = GamePhase.PLACING;
	}
    public ShaxBoard getBoard(){
        return this.shaxBoard;
    }

	public Player getCurrentTurn(){
		return players[currentIndex];
	}

	private Player getNotCurrentTurn(){
		return players[(currentIndex + 1) % 2];
	}

	private void endTurn(){
		this.currentIndex = (currentIndex + 1) % 2; 
	}

	public GamePhase getPhase(){
		return this.currentPhase;
	}

	public Player getWinner(){
		return this.winner;
	}

	public int getCurrentIndex(){
		return this.currentIndex;
	}

	public Player getPlayerFirstMill(){
		return players[firstMill];
	}
	public Boolean getIfFirstMillFound(){
		return firstMillMade;
	}


	public MoveResult makeMove(Move move){

		if (this.getPhase() == GamePhase.ENDED){
			return new Rejected(TheReason.GAME_OVER);
		}

		// check that the move is legit
		ValidationResults result = Referee.isValid(move, shaxBoard, this.getCurrentTurn(), this.getNotCurrentTurn(), this.currentPhase, this.askedForHelp);

		if (!result.isValid()){
			return new Rejected(result.getReason());
		} 

		
		if (move instanceof PlacePiece placePiece){
			if (firstMillMade == false){
				if (BoardUtils.madeMill(placePiece, this.shaxBoard.getBoard())){
					this.firstMillMade = true;
					this.firstMill = this.getCurrentIndex();
				}

			}

			shaxBoard.placePiece(placePiece.getSpotNumber(), this.getCurrentTurn());
			this.getCurrentTurn().placedPiece();

			
			if (this.getCurrentTurn().getPiecesOnBoard() == 12 && this.getNotCurrentTurn().getPiecesOnBoard() == 12){
				this.currentPhase = GamePhase.REMOVING;
				this.currentIndex = this.firstMill;
				this.endTurn();
				
				
			}
			

		} else if (move instanceof RemovePiece removePiece){
			shaxBoard.removePiece(removePiece.getSpotToRemove());
			this.getCurrentTurn().removedPieceFrom(this.getNotCurrentTurn());


			
			if (this.getCurrentTurn().getPiecesOnBoard() <= 11 && this.getNotCurrentTurn().getPiecesOnBoard() <= 11 && this.getNotCurrentTurn().getPiecesOnBoard() >= 3){
				this.currentPhase = GamePhase.MOVING;
			} else if (this.getNotCurrentTurn().getPiecesOnBoard() < 3){
				this.currentPhase = GamePhase.ENDED;
				this.winner = this.getCurrentTurn();
				GameState newGameState = new GameState(this.shaxBoard.getBoard(), this.getPhase(), this.getCurrentTurn());
				newGameState.setWinner(winner);
				MoveResult moveResult = new Applied(newGameState);
				return moveResult;

			}




		} else if (move instanceof MoveSpot moveSpot){


			shaxBoard.moveSpot(moveSpot.getFrom(), moveSpot.getTo(), this.getCurrentTurn());


 			if(!askedForHelp && BoardUtils.madeMill(moveSpot, this.shaxBoard.getBoard())){

				this.currentPhase = GamePhase.REMOVING;
				GameState newGameState = new GameState(this.shaxBoard.getBoard(), this.getPhase(), this.getCurrentTurn());
				MoveResult moveResult = new Applied(newGameState);
				return moveResult;

			}

			if (askedForHelp){
				this.askedForHelp = false;
			}




		} else if (move instanceof AskForHelp){
			 this.askedForHelp = true;

		} else {
			return new Rejected(result.getReason());
		}

 		this.endTurn();
		GameState newGameState = new GameState(this.shaxBoard.getBoard(), this.getPhase(), this.getCurrentTurn());
		MoveResult moveResult = new Applied(newGameState);
		return moveResult;

	
	}


	public void printBoard(){
		String[] c = new String[24];

		for (int i = 0; i < 24; i++){
			Spot spot = shaxBoard.getSpot(String.valueOf(i));
			String name = (spot.getOccupiedBy() != null) ? spot.getOccupiedBy().getPlayer() : null;
			
			c[i] = (name != null) ? " " + name.substring(0, 1) : String.format("%2d", i);
			System.out.println("what I have: " + c[i] + "what shax board has: " + name);
		}


		    System.out.println(c[0]  + "-----------" + c[1]  + "-----------" + c[2]);
		    System.out.println(" |           |           |");
		    System.out.println(" |  " + c[3] + "-------" + c[4] + "-------" + c[5] + "  |");
		    System.out.println(" |   |       |       |   |");
		    System.out.println(" |   |  " + c[6] + "---" + c[7] + "---" + c[8] + "  |   |");
		    System.out.println(" |   |   |       |   |   |");
		    System.out.println(c[9] + "--" + c[10] + "--" + c[11] + "      " + c[12] + "--" + c[13] + "--" + c[14]);
		    System.out.println(" |   |   |       |   |   |");
		    System.out.println(" |   |  " + c[15] + "--" + c[16] + "--" + c[17] + "  |   |");
		    System.out.println(" |   |       |       |   |");
		    System.out.println(" |  " + c[18] + "------" + c[19] + "------" + c[20] + "  |");
		    System.out.println(" |           |           |");
		    System.out.println(c[21] + "----------" + c[22] + "----------" + c[23]);
		}
		
}

