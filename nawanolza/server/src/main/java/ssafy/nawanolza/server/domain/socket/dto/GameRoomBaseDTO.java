package ssafy.nawanolza.server.domain.socket.dto;


import lombok.Data;

@Data
public class GameRoomBaseDTO {

    private Type type;
    protected static enum Type {
        GPS, CHATTING
    }

    public GameRoomBaseDTO(Type type) {
        this.type = type;
    }
}
