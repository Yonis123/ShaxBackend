package com.example.shax_app.Rooms.STOMPControllers;

import com.example.shax_app.Rooms.DTO.GameStateDTO;
import com.example.shax_app.Rooms.DTO.MoveRequest;
import com.example.shax_app.Rooms.RestControllers.RoomController;
import com.example.shax_app.Rooms.RoomLogic.GameEngine.MoveResult;
import com.example.shax_app.Rooms.RoomLogic.GameEngine.Rejected;
import com.example.shax_app.Rooms.RoomLogic.GameEngine.TheReason;
import com.example.shax_app.Rooms.RoomLogic.GameRoom;
import com.example.shax_app.Rooms.Services.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GameSTOMPController {
    private final SimpMessagingTemplate messaging;
    private final RoomManager roomManager;
    private static final Logger log = LoggerFactory.getLogger(GameSTOMPController.class);


    public GameSTOMPController(RoomManager roomManager, SimpMessagingTemplate messaging){
        this.roomManager = roomManager;
        this.messaging = messaging;
    }


    @SubscribeMapping("/room/{roomId}")
    public GameStateDTO getInitialState(@DestinationVariable String roomId) {
        GameRoom room = roomManager.get(roomId);
       try {
           // send state to all listening
           messaging.convertAndSend("/topic/room/" + roomId, room.getState());
           return null;

       } catch(Exception e){
           return new GameStateDTO(null, null, null, null);
       }

    }

    @MessageMapping("/room/{roomId}/move")
    @SendToUser("/queue/errors")
    public TheReason move(@DestinationVariable String roomId,
                          @Header("playerId") String playerId,
                          MoveRequest move){

        GameRoom room = roomManager.get(roomId);

       MoveResult result = room.makeMove(playerId, move);

       if (result instanceof Rejected(TheReason reason)){
           // send to the person who made the request that it was rejected and send back rejected message
           return reason;
       } else {
           // broadcast new gamestateDTO to everyone
           messaging.convertAndSend("/topic/room/" + roomId, room.getState());
           return null;
       }
    }



}
