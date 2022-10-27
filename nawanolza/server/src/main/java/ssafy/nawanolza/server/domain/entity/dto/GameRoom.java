package ssafy.nawanolza.server.domain.entity.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameRoom {
    private String entryCode;
    private Long hostId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public GameRoom(Long hostId, String entryCode) {
        this.hostId = hostId;
        this.entryCode = entryCode;
    }
}
