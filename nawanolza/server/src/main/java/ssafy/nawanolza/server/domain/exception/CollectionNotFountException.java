package ssafy.nawanolza.server.domain.exception;

public class CollectionNotFountException extends RuntimeException {

    private final Long memberId;
    private final Long characterId;

    public CollectionNotFountException(Long memberId, Long characterId) {
        super("해당 유저의 " + characterId + " 컬렉션 정보가 존재하지 않습니다.");
        this.memberId = memberId;
        this.characterId = characterId;
    }
}
