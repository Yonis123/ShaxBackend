package com.example.shax_app.Rooms.RoomLogic;

import com.example.shax_app.Rooms.DTO.GameStateDTO;
import com.example.shax_app.Rooms.DTO.MoveRequest;
import com.example.shax_app.Rooms.RoomLogic.GameEngine.*;

public class GameRoom {

    private final String roomId;
    private Player p1;
    private Player p2;
    private GameModel game;

    public GameRoom(String roomId) {
        this.roomId = roomId;
    }

    public synchronized String addPlayer(String playerId) {
        String existing = tokenOf(playerId);
        if (existing != null) return existing;

        if (p1 == null) { p1 = new Player(playerId, true); startIfReady(); return "P1"; }
        if (p2 == null) { p2 = new Player(playerId); startIfReady(); return "P2"; }
        return null;
    }

    public synchronized void removePlayer(String playerId) {
        if (isPlayer(p1, playerId)) p1 = null;
        if (isPlayer(p2, playerId)) p2 = null;
        // TODO: decide what leaving does to an active game (forfeit, pause, reset)
    }

    public synchronized String tokenOf(String playerId) {
        if (isPlayer(p1, playerId)) return "P1";
        if (isPlayer(p2, playerId)) return "P2";
        return null;
    }

    public synchronized MoveResult makeMove(String playerId, MoveRequest request) {
        Player player = playerFor(playerId);
        if (player == null) throw new IllegalArgumentException("Player is not in this room");
        if (game == null) throw new IllegalStateException("Game hasn't started yet");
        if (request == null || request.type() == null) throw new IllegalArgumentException("Missing move type");

        Move move = switch (request.type()) {
            case PLACE  -> new PlacePiece(player, GamePhase.PLACING, require(request.to(), "to"));
            case MOVE   -> new MoveSpot(player, GamePhase.MOVING, require(request.from(), "from"), require(request.to(), "to"));
            case REMOVE -> new RemovePiece(player, GamePhase.REMOVING, require(request.to(), "to"));
            case HELP   -> new AskForHelp(player, GamePhase.MOVING);
        };
        return game.makeMove(move);
    }

    private static String require(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Missing '" + field + "'");
        return value;
    }

    public synchronized GameStateDTO getState() {
        if (game == null) throw new IllegalStateException("Game hasn't started yet");
        return GameStateDTO.from(game);
    }

    public synchronized boolean isFull()    { return p1 != null && p2 != null; }
    public synchronized boolean isEmpty()   { return p1 == null && p2 == null; }
    public synchronized boolean isStarted() { return game != null; }
    public String getRoomId() { return roomId; }

    // --- helpers (only called from synchronized methods) ---

    private void startIfReady() {
        if (p1 != null && p2 != null && game == null) {
            game = new GameModel(new ShaxBoard(), p1, p2);
        }
    }

    private Player playerFor(String playerId) {
        if (isPlayer(p1, playerId)) return p1;
        if (isPlayer(p2, playerId)) return p2;
        return null;
    }

    private static boolean isPlayer(Player player, String playerId) {
        return player != null && player.getId().equals(playerId);
    }


}