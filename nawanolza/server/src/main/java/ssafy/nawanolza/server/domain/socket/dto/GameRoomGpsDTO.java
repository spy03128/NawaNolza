package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Data;

/**
 * lat: 위도
 * lng: 경도
 * memberId: 멤버 Id
 */
public class GameRoomGpsDTO extends GameRoomBaseDTO {
    private Double lat;
    private Double lng;
    private Long senderId;

    public GameRoomGpsDTO() {
        super(Type.GPS);
    }
}
