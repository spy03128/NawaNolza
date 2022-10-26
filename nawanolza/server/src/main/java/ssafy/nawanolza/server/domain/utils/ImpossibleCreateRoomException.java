package ssafy.nawanolza.server.domain.utils;

public class ImpossibleCreateRoomException extends IllegalStateException {

    public ImpossibleCreateRoomException() {
        super("생성할 수 있는 방의 개수를 넘었습니다.");
    }
}
