package ssafy.nawanolza.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.repository.MemberRepository;
import ssafy.nawanolza.server.domain.service.HideAndGameRoomService;
import ssafy.nawanolza.server.domain.socket.dto.GameCatchDTO;
import ssafy.nawanolza.server.domain.socket.dto.GameEventDTO;
import ssafy.nawanolza.server.domain.socket.dto.GameRoomGpsDTO;
import ssafy.nawanolza.server.domain.socket.dto.GameRoomGpsRangeDTO;
import ssafy.nawanolza.server.dto.CreateGameRequest;
import ssafy.nawanolza.server.dto.HideAndSeekGameRoomResponse;
import ssafy.nawanolza.server.handler.event.GameStartEvent;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameRoomController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final HideAndGameRoomService hideAndGameRoomService;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;


    /*
        /sub/gps/{uuid}    - 구독(roomId:UUID)
        /pub/gps            - GPS 정보 전달
     */

    @MessageMapping("/gps")
    public void gpsSend(GameRoomGpsDTO gameRoomGpsDTO) {
        boolean isItOutRange = hideAndGameRoomService.checkOutOfRange(gameRoomGpsDTO.getEntryCode(),
                gameRoomGpsDTO.getLat(), gameRoomGpsDTO.getLng());
        GameRoomGpsRangeDTO gameRoomGpsRangeDTO = new GameRoomGpsRangeDTO(isItOutRange, gameRoomGpsDTO);
        simpMessageSendingOperations.convertAndSend("/sub/gps/" + gameRoomGpsDTO.getEntryCode() , gameRoomGpsRangeDTO);
    }

    @MessageMapping("/event")
    public void eventBroadCast(GameCatchDTO request) {
        log.info("gameEventDto = {}", request);
        if(GameEventDTO.EventType.CATCH.equals(request.getEventType()))
            hideAndGameRoomService.catchMember(request.getEntryCode(), request.getCatchMemberId());
        GameEventDTO response = request;
        simpMessageSendingOperations.convertAndSend("/sub/event/" + request.getEntryCode(), response);
    }

    @PostMapping("/room/hide")
    public ResponseEntity<HideAndSeekGameRoomResponse> createRoom(@RequestBody CreateGameRequest request) {
        HideAndSeekGameRoom gameRoom = hideAndGameRoomService.createGameRoom(request.getHostId(), request.makeHideAndSeekProperties());
        return ResponseEntity.ok().body(HideAndSeekGameRoomResponse.makeCreateResponse(gameRoom, memberRepository));
    }

    @PostMapping("/room/hide/participation")
    public ResponseEntity<HideAndSeekGameRoomResponse> participateHideAndSeek(@RequestBody Map<String, String> request) {
        long memberId = Long.parseLong(request.get("memberId"));
        String entryCode = request.get("entryCode");

        // 게임 참여
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndGameRoomService.paticipateGameRoom(memberId, entryCode);
        HideAndSeekGameRoomResponse hideAndSeekGameRoomResponse = HideAndSeekGameRoomResponse.makeEntryResponse(hideAndSeekGameRoom, memberRepository);
        simpMessageSendingOperations.convertAndSend("/sub/participate/" + entryCode, hideAndSeekGameRoomResponse.getParticipants());
        return ResponseEntity.ok().body(hideAndSeekGameRoomResponse);
    }

    @GetMapping("/game/start/{entryCode}")
    public ResponseEntity<GameStartEvent> gameStart(@PathVariable String entryCode) {
        GameStartEvent gameStartEvent = hideAndGameRoomService.startGame(entryCode);
        publisher.publishEvent(gameStartEvent);
        log.info("{}번 방의 게임이 시작되었습니다.", entryCode);
        return ResponseEntity.ok(gameStartEvent);
    }
}
