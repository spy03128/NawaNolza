package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * lat: 위도
 * lng: 경도
 * memberId: 멤버 Id
 */
@Getter
@Setter
public class GameRoomGpsDTO extends GameRoomBaseDTO {
    private Double lat;
    private Double lng;

    public GameRoomGpsDTO() {
        super(Type.GPS);
    }
}
