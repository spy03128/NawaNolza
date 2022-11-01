package ssafy.nawanolza.server.domain.entity.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameRoom {
    private String entryCode;
    private Long hostId;

    public GameRoom(Long hostId, String entryCode) {
        this.hostId = hostId;
        this.entryCode = entryCode;
    }
}
