package ssafy.nawanolza.server.domain.exception;

public class TokenEmptyException extends RuntimeException {
    public TokenEmptyException() {
        super("토큰이 존재하지 않습니다.");
    }
}
