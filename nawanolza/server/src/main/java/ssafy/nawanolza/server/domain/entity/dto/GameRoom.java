package ssafy.nawanolza.server.domain.entity.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameRoom {
    private String roomId;
    private String entryCode;
    private Long hostId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public GameRoom(String roomId, Long hostId) {
        this.roomId = roomId;
        this.hostId = hostId;
        entryCode = makeEntryCode();
    }

    private String makeEntryCode() {
        Random random = new Random();
        StringBuilder entryCode = new StringBuilder();
        for(int i = 0; i < 4; i++) entryCode.append(random.nextInt(10));
        return entryCode.toString();
    }
}
