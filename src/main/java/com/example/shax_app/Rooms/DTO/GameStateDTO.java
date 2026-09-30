package com.example.shax_app.Rooms.DTO;


import com.example.shax_app.Rooms.Enums.OccupiedBy;
import com.example.shax_app.Rooms.Enums.Phase;
import com.example.shax_app.Rooms.RoomLogic.GameEngine.GameModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public record GameStateDTO(
        Map<String, String> board,
        String currentTurn,
        String phase,
//        String pendingCapture,
        String winner
//        int moveNumber
) {

    public static GameStateDTO from(GameModel game){

        Map<String, String> boardState = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++){
            String spotNumber = String.valueOf(i);
            String spot = null;
            if (game.getBoard().getSpot(spotNumber).getOccupiedBy() != null){
                spot = game.getBoard().getSpot(spotNumber).getOccupiedBy().getPlayerName();
            }

            boardState.put(spotNumber, spot);

        }

        String winner = null;

        if (game.getWinner() != null){
            winner = game.getWinner().getPlayerName();
        }

        return new GameStateDTO(
            boardState, game.getCurrentTurn().getPlayerName(), game.getPhase().toString(), winner
        );
    }

}



