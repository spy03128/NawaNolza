package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;
import ssafy.nawanolza.server.domain.exception.CreateGameRoomLimitException;
import ssafy.nawanolza.server.domain.exception.GameRoomNotFoundException;
import ssafy.nawanolza.server.domain.repository.HideAndSeekGameRoomRepository;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class HideAndGameRoomService {
    private final HideAndSeekGameRoomRepository hideAndSeekGameRoomRepository;


    public HideAndSeekGameRoom createGameRoom(Long hostId, HideAndSeekProperties hideAndSeekProperties) {
        if (hideAndSeekGameRoomRepository.count() > 100)
            throw new CreateGameRoomLimitException();
        String entryCode = CreateRoomUtil.issueEntryCode(hideAndSeekGameRoomRepository);
        HideAndSeekGameRoom createGameRoom = HideAndSeekGameRoom.create(hostId, hideAndSeekProperties, entryCode);
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

    public void deleteGameRoom(String entryCode) {
        HideAndSeekGameRoom hideAndSeekGameRoom = hideAndSeekGameRoomRepository.findById(entryCode).orElseThrow(() -> new GameRoomNotFoundException());
        hideAndSeekGameRoomRepository.delete(hideAndSeekGameRoom);
    }

    public void catchMember(String entryCode, Long memberId) {
    }
}
