package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.History;

import java.util.List;


public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByCollection(Collection collection);
}
