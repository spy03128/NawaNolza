package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Data;

@Data
public class GameEventDTO {

    private String entryCode;
    private Long senderId;
    private Type type;
    private EventType eventType;

    public enum Type {
        GPS, CHAT, EVENT
    }

    public enum EventType {
        ALARM, CATCH
    }
}
