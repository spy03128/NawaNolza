package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameEventDTO extends GameRoomBaseDTO {

    private EventType eventType;

    protected enum EventType {
        ALARM
    }
}
