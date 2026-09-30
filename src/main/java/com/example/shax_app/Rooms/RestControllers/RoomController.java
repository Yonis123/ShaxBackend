package com.example.shax_app.Rooms.RestControllers;



import com.example.shax_app.Rooms.DTO.RoomInfoDTO;
import com.example.shax_app.Rooms.DTO.GameStateDTO;
import com.example.shax_app.Rooms.RoomLogic.GameRoom;
import com.example.shax_app.Rooms.Services.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;




@Controller
public class RoomController {
//
    private final RoomManager roomManager;

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

//
    public RoomController(RoomManager roomManager, SimpMessagingTemplate messaging){
        this.roomManager = roomManager;

    }

//    @MessageMapping("/rooms/{roomId}")
//    public void join(@DestinationVariable String roomId,
//                     @Payload SampleMessage broadcast){
//
//
//
//
//        StringBuilder to = new StringBuilder();
//        to.append("/topic/room/");
//        to.append(roomId);
//
//        Message returnMsg = new Message(broadcast.getPlayerId(), "just send a broadcast now");
//
//
//        messaging.convertAndSend(to.toString(), returnMsg);
//
////        GameRoom room = roomManager.getOrCreate(roomId);
////        boolean joined = room.addPlayer(msg.getPlayerId());
////
////        if (!joined){
////           messaging.convertAndSendToUser(
////                   headers.getSessionId(),
////                   "/queue/errors",
////                   Map.of("error", "Room is full")
////           );
////        }
//
//    }

    @PostMapping("/api/v1/games")
    @ResponseBody
    public ResponseEntity<?> createRoom(){
        //this should be a POST request i don't need nothing and I create room
        String newRoomId = UUID.randomUUID().toString();
        roomManager.createRoom(newRoomId);
        GameRoom room = roomManager.get(newRoomId);

        String playerId = UUID.randomUUID().toString();

        room.addPlayer(playerId);






        log.info("created room with roomId: " + newRoomId + " and add player with Id: " + playerId + " to the room");

        //i need to still open the websocket

        return ResponseEntity.ok().body(new RoomInfoDTO(newRoomId, playerId));


    }

//    @PostMapping("/api/v1/{roomId}/getPlayerID")
//    @ResponseBody
//    public ResponseEntity<?> joinRoom(
//            @PathVariable String roomId
//    ){
//
//        GameRoom room = roomManager.get(roomId);
//
//        if (room == null || room.isFull()){
//            return ResponseEntity.badRequest().body("invalid room request");
//        }
//
//
//        String playerId = UUID.randomUUID().toString();
//
//
//        room.addPlayer(playerId);
//
//        log.info("a player joined with playerId: " + playerId + " joined roomId: " + room.getRoomId());
//
//
//        //I still need to let them join the websocket that was created
//
//
//        return ResponseEntity.ok().body(playerId);
//
//
//    }

    @GetMapping("/api/v1/games/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getGame(@PathVariable String roomId){
        GameRoom room = roomManager.get(roomId);

        if(room == null){
            return ResponseEntity.badRequest().body("ROOM_NOT_FOUND");
        } else {

            GameStateDTO state = room.getState();
            return ResponseEntity.ok().body(state);

        }
    }

    @PostMapping("/api/v1/rooms/{roomId}/join")
    @ResponseBody
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){


        GameRoom room = roomManager.get(roomId);

        if (room == null){
            return ResponseEntity.badRequest().body("ROOM_NOT_FOUND");
        }

        String playerId = UUID.randomUUID().toString();

        String player = room.addPlayer(playerId);

        if (player == null){
            return ResponseEntity.badRequest().body("ROOM_FULL");
        }

        log.info("player with ID: " + playerId + " has joined the room: " + roomId);

        return ResponseEntity.ok().body(new RoomInfoDTO(roomId, playerId));




    }





}
