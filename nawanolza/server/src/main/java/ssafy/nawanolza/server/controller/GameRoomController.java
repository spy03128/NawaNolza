package ssafy.nawanolza.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.repository.MemberRepository;
import ssafy.nawanolza.server.domain.service.HideAndGameRoomService;
import ssafy.nawanolza.server.domain.socket.dto.GameEventDTO;
import ssafy.nawanolza.server.domain.socket.dto.GameRoomGpsDTO;
import ssafy.nawanolza.server.dto.CreateGameRequest;
import ssafy.nawanolza.server.dto.CreateGameRoomResponse;
import ssafy.nawanolza.server.dto.EntryGameRoomResponse;
import ssafy.nawanolza.server.dto.MemberDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameRoomController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final HideAndGameRoomService hideAndGameRoomService;
    private final MemberRepository memberRepository;

    /*
        /sub/gps/{uuid}    - 구독(roomId:UUID)
        /pub/gps            - GPS 정보 전달
     */

    @MessageMapping("/gps")
    public void gpsSend(GameRoomGpsDTO gameRoomGpsDTO) {
        simpMessageSendingOperations.convertAndSend("/sub/gps/" + gameRoomGpsDTO.getGameRoomId() , gameRoomGpsDTO);
    }

    @MessageMapping("/event")
    public void eventBroadCast(GameEventDTO gameEventDTO) {
        simpMessageSendingOperations.convertAndSend("/sub/event" + gameEventDTO.getGameRoomId(), gameEventDTO);
    }

    @PostMapping("/hideandseek")
    public ResponseEntity<CreateGameRoomResponse> createRoomResponse(@RequestBody CreateGameRequest request) {
        HideAndSeekGameRoom gameRoom = hideAndGameRoomService.createGameRoom(request.getHostId(), request.makeHideAndSeekProperties());
        return ResponseEntity.ok().body(new CreateGameRoomResponse(gameRoom));
    }

    @PostMapping("/hideandseek/participation")
    public ResponseEntity<EntryGameRoomResponse> participateHideAndSeek(@RequestBody Map<String, String> request) {
        long memberId = Long.parseLong(request.get("memberId"));
        String entryCode = request.get("entryCode");

        // 게임 참여
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndGameRoomService.paticipateGameRoom(memberId, entryCode);
        List<MemberDto> members = memberRepository.findAllByMemberId(hideAndSeekGameRoom.getParticipants()).stream().map(MemberDto::new).collect(Collectors.toList());
        simpMessageSendingOperations.convertAndSend("/sub/participate" + entryCode, members);
        return ResponseEntity.ok().body(new EntryGameRoomResponse(hideAndSeekGameRoom, members));
    }

}
