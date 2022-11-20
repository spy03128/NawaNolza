package ssafy.nawanolza.server.domain.exception;

public class AllMarkerLockException extends IllegalStateException{

    public AllMarkerLockException() { super("잠시 후, 다시 시도해 주세요."); }
}
