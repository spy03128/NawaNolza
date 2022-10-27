package ssafy.nawanolza.server.domain.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

import java.util.UUID;

@Getter @Setter
public class HideAndSeekGameRoom extends GameRoom {

    @Builder
    private HideAndSeekGameRoom(Long hostId, String entryCode) {
        super(hostId, entryCode);
    }

    public static HideAndSeekGameRoom create(Long hostId, CreateRoomUtil createRoomUtil) {
        return HideAndSeekGameRoom.builder()
                .hostId(hostId)
                .entryCode(createRoomUtil.issueEntryCode())
                .build();
    }
}
