package ssafy.nawanolza.server.domain.exception;

public class CreateGameRoomLimitException extends IllegalStateException {
    public CreateGameRoomLimitException() {
        super("생성 한도가 가득 차서 방 생성을 하지 못합니다.");
    }
}
