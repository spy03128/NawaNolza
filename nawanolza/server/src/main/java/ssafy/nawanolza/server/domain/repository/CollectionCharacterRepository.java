package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.nawanolza.server.domain.entity.Collection;

import java.util.List;

public interface CollectionCharacterRepository extends JpaRepository<Collection, Long> {
    @Query(value = "select b.character_id as characterId, b.rare, a.current_level as currentLevel " +
                    "from (select c.* " +
                           "from collection c " +
                           "where c.member_id=:memberId) as a " +
                    "right outer join characters b on a.character_id=b.character_id " +
                    "order by a.current_level desc ", nativeQuery = true)
    List<CollectionCharacterDto> findAllSortByLevel(@Param("memberId") Long memberId);

    @Query(value = "select b.character_id as characterId, b.rare, a.current_level as currentLevel " +
                    "from (select c.* " +
                           "from collection c " +
                           "where c.member_id=:memberId) as a " +
                    "right outer join characters b on a.character_id=b.character_id " +
                    "order by b.character_id ", nativeQuery = true)
    List<CollectionCharacterDto> findAllSortByCharacterId(@Param("memberId") Long memberId);

    @Query(value = "select b.character_id as characterId, b.rare, a.current_level as currentLevel " +
                    "from (select c.* " +
                           "from collection c " +
                           "where c.member_id=:memberId) as a " +
                    "right outer join characters b on a.character_id=b.character_id " +
                    "where b.character_id in (select ct.character_id " +
                                              "from types t join character_type ct on t.type_id = ct.type_id " +
                                              "where t.name = :type) " +
                    "order by a.current_level desc", nativeQuery = true)
    List<CollectionCharacterDto> findAllSortByLevelFilterByType(@Param("type") String type, @Param("memberId") Long memberId);

    @Query(value = "select b.character_id as characterId, b.rare, a.current_level as currentLevel " +
            "from (select c.* " +
                  "from collection c " +
                  "where c.member_id= :memberId) as a " +
            "right outer join characters b on a.character_id=b.character_id " +
            "where b.character_id in (select ct.character_id " +
                                      "from types t join character_type ct on t.type_id = ct.type_id " +
                                      "where t.name = :type) " +
            "order by b.character_id ", nativeQuery = true)
    List<CollectionCharacterDto> findAllSortByCharacterIdFilterByType(@Param("type") String type, @Param("memberId") Long memberId);

    interface CollectionCharacterDto {
        Long getCharacterId();
        int getRare();
        Integer getCurrentLevel();
    }
}
