package com.example.shax_app.Rooms.DTO;

public record MoveRequest(MoveType type, String from, String to) {

    public enum MoveType { PLACE, MOVE, REMOVE, HELP }
}