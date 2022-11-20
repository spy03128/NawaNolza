package ssafy.nawanolza.server.handler.event;

import lombok.Getter;

@Getter
public class GameEndEvent {
    String message = "방장이 게임을 종료하였습니다. 방이 삭제되었습니다.";
    String entryCode;

    public GameEndEvent(String entryCode) {
        this.entryCode = entryCode;
    }
}
