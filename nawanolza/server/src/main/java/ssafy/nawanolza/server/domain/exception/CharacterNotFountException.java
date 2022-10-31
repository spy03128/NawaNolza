package ssafy.nawanolza.server.domain.exception;

public class CharacterNotFountException extends RuntimeException {

    private final Long characterId;

    public CharacterNotFountException(Long characterId) {
        super("요청하신 캐릭터 정보가 존재하지 않습니다.");
        this.characterId = characterId;
    }
}
