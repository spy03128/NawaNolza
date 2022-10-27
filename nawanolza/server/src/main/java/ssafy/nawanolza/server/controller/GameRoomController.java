package ssafy.nawanolza.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import ssafy.nawanolza.server.domain.socket.dto.GameRoomGpsDTO;

@Controller
@RequiredArgsConstructor
public class GameRoomController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /*
        /sub/gps/{uuid}    - 구독(roomId:UUID)
        /pub/gps            - GPS 정보 전달
     */

    @MessageMapping("/gps")
    public void gpsSend(GameRoomGpsDTO gameRoomGpsDTO) {
        simpMessageSendingOperations.convertAndSend("/sub/room/" + gameRoomGpsDTO.getGameRoomId() + "/gps", gameRoomGpsDTO);
    }

}
