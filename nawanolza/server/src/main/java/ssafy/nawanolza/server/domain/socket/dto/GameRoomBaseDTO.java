package ssafy.nawanolza.server.domain.socket.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class GameRoomBaseDTO {

    private String gameRoomId;
    private Long senderId;
    private Type type;
    protected enum Type {
        GPS, CHATTING
    }

    public GameRoomBaseDTO(Type type) {
        this.type = type;
    }
}
