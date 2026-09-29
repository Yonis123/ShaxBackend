package com.example.shax_app.Rooms.STOMPControllers;

import com.example.shax_app.Rooms.DTO.GameStateDTO;
import com.example.shax_app.Rooms.RestControllers.RoomController;
import com.example.shax_app.Rooms.RoomLogic.GameRoom;
import com.example.shax_app.Rooms.Services.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameSTOMPController {
    private final SimpMessagingTemplate messaging;
    private final RoomManager roomManager;
    private static final Logger log = LoggerFactory.getLogger(GameSTOMPController.class);


    public GameSTOMPController(RoomManager roomManager, SimpMessagingTemplate messaging){
        this.roomManager = roomManager;
        this.messaging = messaging;
    }


    @SubscribeMapping("/room/{roomId}/state")
    public GameStateDTO getInitialState(@DestinationVariable String roomId) {
        GameRoom room = roomManager.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found: " + roomId);
        }
        return room.getState();
    }


}
