package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.Member;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;
import ssafy.nawanolza.server.domain.exception.CreateGameRoomLimitException;
import ssafy.nawanolza.server.domain.exception.GameRoomNotFoundException;
import ssafy.nawanolza.server.domain.exception.MemberNotFountException;
import ssafy.nawanolza.server.domain.repository.HideAndSeekGameRoomRepository;
import ssafy.nawanolza.server.domain.repository.MemberRepository;
import ssafy.nawanolza.server.handler.event.GameEndEvent;
import ssafy.nawanolza.server.handler.event.GameFinishEvent;
import ssafy.nawanolza.server.handler.event.GameStartEvent;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HideAndGameRoomService {
    private final HideAndSeekGameRoomRepository hideAndSeekGameRoomRepository;
    private final MemberRepository memberRepository;


    public HideAndSeekGameRoom createGameRoom(Long hostId, HideAndSeekProperties hideAndSeekProperties) {
        if (hideAndSeekGameRoomRepository.count() > 100)
            throw new CreateGameRoomLimitException();
        String entryCode = CreateRoomUtil.issueEntryCode(hideAndSeekGameRoomRepository);
        HideAndSeekGameRoom createGameRoom = HideAndSeekGameRoom.create(hostId, hideAndSeekProperties,
                entryCode);
        HideAndSeekGameRoom save = hideAndSeekGameRoomRepository.save(createGameRoom);
        log.info("createGameRoom = {}", save);
        return save;
    }

    public HideAndSeekGameRoom paticipateGameRoom(Long memberId, String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        HideAndSeekGameRoom updateGameRoom = hideAndSeekGameRoom.participateMember(memberId);
        hideAndSeekGameRoomRepository.save(hideAndSeekGameRoom);
        return updateGameRoom;
    }

    /*
     * 게임 시간 전달, 게임 술래 전달
     * */
    public GameStartEvent startGame(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom =
                hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        Map<String, Object> roles = hideAndSeekGameRoom.startGame();
        GameStartEvent gameStartDTO = GameStartEvent.of(entryCode, (Long) roles.get("tagger"),
                (List) roles.get("runners"), hideAndSeekGameRoom.getStartTime(),
                hideAndSeekGameRoom.getHideAndSeekProperties().getPlayTime(), hideAndSeekGameRoom.getLat(),
                hideAndSeekGameRoom.getLng(), hideAndSeekGameRoom.getRange());
        hideAndSeekGameRoomRepository.save(hideAndSeekGameRoom);
        return gameStartDTO;
    }

    public GameFinishEvent finishedGame(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom =
                hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        boolean isWinTagger = winnerIsTagger(hideAndSeekGameRoom);
        GameFinishEvent finishEvent = new GameFinishEvent(entryCode, isWinTagger);
        List<Long> winnerList;
        if (isWinTagger) {
            winnerList = hideAndSeekGameRoom.getTaggers();
        } else {
            winnerList = hideAndSeekGameRoom.getRunners();
        }
        List<Member> winners = memberRepository.findAllByMemberId(winnerList).orElseThrow(() -> new MemberNotFountException(winnerList));
        for (Member winner :winners) {
            System.out.println(winner.getName());
            finishEvent.make(winner.getName(), winner.getImage());
        }
        return finishEvent;
    }

    public GameEndEvent deleteRoom(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom =
                hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        hideAndSeekGameRoomRepository.delete(hideAndSeekGameRoom);
        return new GameEndEvent(entryCode);
    }


    /**
     * true : 술래 승리, false : 숨는 팀 승리
     */
    private boolean winnerIsTagger(HideAndSeekGameRoom hideAndSeekGameRoom) {

        // 숨는 팀 중 안잡힌 사람이 있는지 확인
        for (Boolean isCatched : hideAndSeekGameRoom.getStatus().values()) {
            if (!isCatched) {
                return false;
            }
        }
        return true;
    }

    public void deleteGameRoom(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        hideAndSeekGameRoomRepository.delete(hideAndSeekGameRoom);
    }

    public void catchMember(String entryCode, Long memberId) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        hideAndSeekGameRoom.seekRunner(memberId);
        hideAndSeekGameRoomRepository.save(hideAndSeekGameRoom);
    }

    public boolean checkOutOfRange(String entryCode, double currentLat, double currentLng) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        if (getDistance(hideAndSeekGameRoom.getLat(), hideAndSeekGameRoom.getLng(), currentLat, currentLng) >
                hideAndSeekGameRoom.getRange()) return true;
        return false;
    }

    public double getDistance(double centerLat, double centerLng, double currentLat, double currentLng) {
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(centerLat - currentLat) * toRadian;
        double deltaLongitude = Math.abs(centerLng - currentLng) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);

        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(centerLat * toRadian) * Math.cos(currentLat * toRadian) * sinDeltaLng * sinDeltaLng);

        double distance = 2 * radius * Math.asin(squareRoot);
        return distance;
    }
}
