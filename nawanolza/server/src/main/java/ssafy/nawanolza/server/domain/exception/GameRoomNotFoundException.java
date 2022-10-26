package ssafy.nawanolza.server.domain.exception;

public class GameRoomNotFoundException extends IllegalArgumentException {

    public GameRoomNotFoundException() {
        super("요청하시 게임방 정보가 존재하지 않습니다.");
    }
}
