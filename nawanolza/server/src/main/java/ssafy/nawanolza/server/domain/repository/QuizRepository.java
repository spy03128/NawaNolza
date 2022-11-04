package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.nawanolza.server.domain.entity.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM quiz order by RAND() limit 1",nativeQuery = true)
    List<Quiz> findQuizRandomOne();
}
