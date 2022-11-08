package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;
import ssafy.nawanolza.server.domain.exception.CreateGameRoomLimitException;
import ssafy.nawanolza.server.domain.exception.GameRoomNotFoundException;
import ssafy.nawanolza.server.domain.repository.HideAndSeekGameRoomRepository;
import ssafy.nawanolza.server.handler.event.GameStartEvent;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HideAndGameRoomService {
    private final HideAndSeekGameRoomRepository hideAndSeekGameRoomRepository;


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
        GameStartEvent gameStartDTO = new GameStartEvent(entryCode, (Long) roles.get("tagger"),
                (List) roles.get("runners"), hideAndSeekGameRoom.getStartTime());
        return gameStartDTO;
    }

    public void deleteGameRoom(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        hideAndSeekGameRoomRepository.delete(hideAndSeekGameRoom);
    }

    public void catchMember(String entryCode, Long memberId) {

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
