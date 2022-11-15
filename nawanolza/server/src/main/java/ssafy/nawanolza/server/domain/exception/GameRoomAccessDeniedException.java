package ssafy.nawanolza.server.domain.exception;

public class GameRoomAccessDeniedException extends IllegalArgumentException{
    public GameRoomAccessDeniedException() {
        super("이미 게임이 진행중인 방입니다.");
    }
}
