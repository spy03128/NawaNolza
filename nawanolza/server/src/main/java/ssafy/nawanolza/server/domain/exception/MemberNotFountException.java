package ssafy.nawanolza.server.domain.exception;

public class MemberNotFountException extends RuntimeException {

    private final String email;

    public MemberNotFountException(String email) {
        super("요청하신 회원정보가 존재하지 않습니다.");
        this.email = email;
    }
}
