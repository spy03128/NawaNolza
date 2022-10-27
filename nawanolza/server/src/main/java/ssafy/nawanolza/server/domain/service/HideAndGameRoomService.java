package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.dto.GameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.exception.CreateGameRoomLimitException;
import ssafy.nawanolza.server.domain.exception.GameRoomNotFoundException;
import ssafy.nawanolza.server.domain.repository.GameRoomRepository;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

@Service
@RequiredArgsConstructor
public class HideAndGameRoomService {

    private final CreateRoomUtil createRoomUtill;
    private final GameRoomRepository gameRoomRepository;

    public HideAndSeekGameRoom createGameRoom(Long hostId) {
        if (!gameRoomRepository.isCreatableGameRoom())
            throw new CreateGameRoomLimitException();
        HideAndSeekGameRoom createGameRoom = HideAndSeekGameRoom.create(hostId, createRoomUtill);
        return (HideAndSeekGameRoom) gameRoomRepository.save(createGameRoom);
    }

    public void deleteGameRoom(String entryCode) {
        GameRoom gameRoom = gameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        gameRoomRepository.remove(gameRoom);
        createRoomUtill.retrieveEntryCode(entryCode);
    }
}
