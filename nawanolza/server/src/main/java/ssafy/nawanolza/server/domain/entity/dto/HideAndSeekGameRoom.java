package ssafy.nawanolza.server.domain.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class HideAndSeekGameRoom extends GameRoom {

    @Builder
    private HideAndSeekGameRoom(String roomId, Long hostId) {
        super(roomId, hostId);
    }

    public static HideAndSeekGameRoom create(Long hostId) {
        return HideAndSeekGameRoom.builder().roomId(UUID.randomUUID().toString()).hostId(hostId).build();
    }
}
