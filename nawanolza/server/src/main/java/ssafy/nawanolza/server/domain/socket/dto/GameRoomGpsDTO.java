package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Data;

/**
 * lat: 위도
 * lng: 경도
 * memberId: 멤버 Id
 */
@Data
public class GameRoomGpsDTO {
    private Double lat;
    private Double lng;
    private Long memberId;
}
