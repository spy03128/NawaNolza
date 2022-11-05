package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.nawanolza.server.domain.entity.Collection;

import java.util.Optional;


public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("select c from Collection c join fetch c.member m join fetch c.character ch where m.id = :memberId and ch.id = :characterId")
    Optional<Collection> findByMemberIdAndCharacterId(@Param("memberId") Long memberId, @Param("characterId") Long characterId);
}
