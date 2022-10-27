package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ssafy.nawanolza.server.domain.entity.dto.Marker;

public interface MapCharacterRedisRepository extends CrudRepository<Marker, Long> {
}
