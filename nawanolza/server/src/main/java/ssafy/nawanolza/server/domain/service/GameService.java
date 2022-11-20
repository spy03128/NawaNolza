package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.Game;
import ssafy.nawanolza.server.domain.repository.GameRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> findGame() {
        return gameRepository.findAll();
    }
}
