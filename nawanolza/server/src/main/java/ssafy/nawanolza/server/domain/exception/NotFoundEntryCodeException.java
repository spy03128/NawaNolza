package ssafy.nawanolza.server.domain.exception;

public class NotFoundEntryCodeException extends IllegalArgumentException {

    public NotFoundEntryCodeException() {
        super("입력하신 방코드가 존재하지 않거나 만료되었습니다.");
    }
}
