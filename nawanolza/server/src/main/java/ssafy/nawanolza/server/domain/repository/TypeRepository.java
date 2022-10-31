package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.nawanolza.server.domain.entity.Type;

import java.util.List;


public interface TypeRepository extends JpaRepository<Type, Long> {
    @Query("select t.name from Type t where t.id in :type")
    List<String> findAllByType(@Param("type") List<Long> type);
}
