package ssafy.nawanolza.server.domain.socket.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class GameRoomBaseDTO {

    private String entryCode;
    private Long senderId;
    private Type type;
    protected enum Type {
        GPS, CHAT, EVENT
    }

    public GameRoomBaseDTO(Type type) {
        this.type = type;
    }
}
