package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Getter;

@Getter
public class GameRoomGpsRangeDTO extends GameRoomGpsDTO {
    private boolean outOfRange;

    public GameRoomGpsRangeDTO(boolean isItRange, GameRoomGpsDTO gameRoomGpsDTO) {
        this.outOfRange = isItRange;
        super.setLat(gameRoomGpsDTO.getLat());
        super.setLng(gameRoomGpsDTO.getLng());
        super.setEntryCode(gameRoomGpsDTO.getEntryCode());
        super.setSenderId(gameRoomGpsDTO.getSenderId());
        super.setType(gameRoomGpsDTO.getType());
    }
}
