package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.nawanolza.server.domain.entity.Character;

import java.util.List;


public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findCharactersByRareIsFalse();

    List<Character> findCharactersByRareIsTrue();
}
