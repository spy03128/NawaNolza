package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.entity.CharacterType;

import java.util.List;


public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    @Query("select ct.type.id from CharacterType ct where ct.character=:character")
    List<Long> findAllByCharacter(@Param("character") Character character);
}
