package ssafy.nawanolza.server.dto;

import lombok.Data;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;

import java.util.List;

@Data
public class CreateGameRoomResponse {

    private String message = "방 생성을 성공하였습니다.";
    private String entryCode;
    private HideAndSeekProperties hideAndSeekProperties;
    private List<Long> participants;

    public CreateGameRoomResponse(HideAndSeekGameRoom gameRoom) {
        this.entryCode = gameRoom.getEntryCode();
    }
}
