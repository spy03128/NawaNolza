package ssafy.nawanolza.server.domain.exception;

public class UnderstaffedException extends IllegalStateException {

    public UnderstaffedException(int paticpantCount) {
        super("숨바꼭질 인원은 최소 2명 이상이어야 합니다. 현재인원 : " + paticpantCount);
    }
}