package com.example.shax_app.Rooms.RoomLogic.GameEngine;

public class Player {
    private String playerId;
    private int piecesOnBoard;
    private boolean isPlayer1 = false;

    public Player(String playerId) {
        this.playerId = playerId;
    }

    public Player(String playerId, boolean isPlayer1){
        this.playerId = playerId;
        this.isPlayer1 = isPlayer1;
    }

    public String getPlayer() {
        return playerId;
    }

    public String getId() {return playerId;}


    public int getPiecesOnBoard(){
        return piecesOnBoard;
    }

    public void placedPiece(){
        this.piecesOnBoard++;
    }

    public void pieceRemoved(){
        this.piecesOnBoard -= 1;
    }

    public void removedPieceFrom(Player opponent){
        opponent.pieceRemoved();
    }

    public String getPlayerName(){
        if (isPlayer1){
            return "P1";
        }

        return "P2";

    }



}
