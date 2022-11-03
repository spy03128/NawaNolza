package ssafy.nawanolza.server.domain.socket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameCatchDTO extends GameEventDTO {
    private Long catchMemberId;
}
