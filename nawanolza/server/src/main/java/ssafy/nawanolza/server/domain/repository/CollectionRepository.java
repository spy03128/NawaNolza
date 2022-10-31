package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.nawanolza.server.domain.entity.Collection;

import java.util.Optional;


public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Optional<Collection> findByMemberIdAndCharacterId(Long memberId, Long characterId);
}
