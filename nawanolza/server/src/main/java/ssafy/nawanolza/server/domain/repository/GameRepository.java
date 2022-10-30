package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.nawanolza.server.domain.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
