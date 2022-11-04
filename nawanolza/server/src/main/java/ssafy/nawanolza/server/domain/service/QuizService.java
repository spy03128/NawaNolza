package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.Quiz;
import ssafy.nawanolza.server.domain.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz getQuiz(int questType) {
        return  questType == 0 ? quizRepository.findQuizRandomOne().get(0) : null;
    }
}
