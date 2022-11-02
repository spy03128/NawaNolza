package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.repository.CharacterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;


    public List<Character> findNormalCharacters() {
        return characterRepository.findCharactersByRareIsFalse();
    }

    public List<Character> findRareCharacters() {
        return characterRepository.findCharactersByRareIsTrue();
    }
}
