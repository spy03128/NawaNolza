package ssafy.nawanolza.server.domain.exception;

public class GameRoomOverflowException extends IllegalStateException {

    public GameRoomOverflowException() {
        super("입장할 수 있는 인원을 초과하였습니다.");
    }
}
