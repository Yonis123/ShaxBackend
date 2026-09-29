package com.example.shax_app.Rooms.Services;

import com.example.shax_app.Rooms.RoomLogic.GameRoom;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomManager {
    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public GameRoom getOrCreate(String roomId){
        return rooms.computeIfAbsent(roomId, GameRoom::new);
    }

    public void createRoom(String roomId){
        GameRoom room = new GameRoom(roomId);
        rooms.put(roomId, room);
    }

    public GameRoom get(String roomId) { return rooms.get(roomId); }
    public void remove(String roomId) { rooms.remove(roomId); }

}
