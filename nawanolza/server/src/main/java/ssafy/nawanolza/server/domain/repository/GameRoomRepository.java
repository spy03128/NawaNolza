package ssafy.nawanolza.server.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ssafy.nawanolza.server.domain.entity.dto.GameRoom;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class GameRoomRepository {

    private Map<String, GameRoom> gameRoomMap;

    @PostConstruct
    private void init() {
        gameRoomMap = new LinkedHashMap<>();
    }

    public Optional<GameRoom> findById(String entryCode) {
        GameRoom room = null;
        try {
            room = gameRoomMap.get(entryCode);
        } catch (NullPointerException e) {
            log.error("등록되지 않은 방정보입니다.");
        }
        return Optional.of(room);
    }

    public GameRoom save(GameRoom room) {
        gameRoomMap.put(room.getEntryCode(), room);
        return room;
    }

    public void remove(GameRoom room) {
        try {
            gameRoomMap.remove(room.getEntryCode());
        } catch (NullPointerException e) {
            log.error("등록되지 않은 방정보입니다.");
            throw e;
        }
    }
}
