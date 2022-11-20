package ssafy.nawanolza.server.domain.entity.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameRoom {

    @Id
    private String entryCode;
    private Long hostId;

    public GameRoom(Long hostId, String entryCode) {
        this.hostId = hostId;
        this.entryCode = entryCode;
    }
}
