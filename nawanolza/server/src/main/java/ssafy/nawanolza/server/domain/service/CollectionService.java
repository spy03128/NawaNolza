package ssafy.nawanolza.server.domain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.*;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.exception.CharacterNotFountException;
import ssafy.nawanolza.server.domain.exception.CollectionNotFountException;
import ssafy.nawanolza.server.domain.exception.MemberNotFountException;
import ssafy.nawanolza.server.domain.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final MemberRepository memberRepository;
    private final CollectionCustomRepositoryImpl collectionCustomRepository;
    private final CollectionRepository collectionRepository;
    private final TypeRepository typeRepository;
    private final CharacterTypeRepository characterTypeRepository;
    private final HistoryRepository historyRepository;
    private final MapCharacterRedisRepository mapCharacterRedisRepository;
    private final CharacterRepository characterRepository;
    private final GameRepository gameRepository;

    private final int MAX_COUNT_MARKERS = 100;
    private final int MAX_RADIUS = 32000;
    private final double LNG = 128.45703803722398;
    private final double LAT = 35.98030869215386;


    public List<Collection> getCollection(Long memberId, String type, String sort){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFountException(memberId));

        return collectionCustomRepository.getCollection(memberId, type, sort);
    }

    public Character getCharacterDetail(Long characterId){
        return characterRepository.findById(characterId)
                .orElseThrow(() -> new CharacterNotFountException(characterId));
    }

    public Collection getCollectionDetail(Long memberId, Long characterId){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFountException(memberId));

        Optional<Collection> collection = collectionRepository.findByMemberIdAndCharacterId(findMember.getId(), characterId);

        return collection.isEmpty() ? null : collection.get();
    }

    public List<String> getTypesDetail(Character character){
        return typeRepository.findAllByType(characterTypeRepository.findAllByCharacter(character));
    }

    public List<History> getHistoryDetail(Collection collection){
        return historyRepository.findAllByCollection(collection);
    }

    public List<Marker> getMapCharacters(){
        return (List<Marker>) mapCharacterRedisRepository.findAll();
    }

    /*
     * 마커 만들기
     * 성능 최적화 필요
     * */
    public void makeMarker(){
        List<Character> normalCharacters = characterRepository.findCharactersByRareIsFalse();
        List<Character> rareCharacters = characterRepository.findCharactersByRareIsTrue();
        List<Game> games = gameRepository.findAll();

        for (int i = 0; i < MAX_COUNT_MARKERS; i++) {
            Character selectCharacter = randomCharacter(rareCharacters, normalCharacters);
            int quest = randomQuest(games.size());
            int gameTime = 0;
            if (quest > 0)
                gameTime = games.get(quest).getTime();
            LatLng location = getRandomLocation();

            Marker newMarker = Marker.builder().characterId(selectCharacter.getId())
                    .rare(selectCharacter.isRare()).questType(quest)
                    .time(gameTime).lat(location.lat).lng(location.lng).build();

            mapCharacterRedisRepository.save(newMarker);
        }
    }

    /*
     * 랜덤하게 캐릭터 선택
     * 레어 10%, 노말 90% 확률
     * */
    private Character randomCharacter(List<Character> rareCharacters, List<Character> normalCharacters) {
        Random random = new Random();
        int selectNumber = random.nextInt(9);       // 0 : 레어 캐릭터 1~9 : 노말 캐릭터

        if (selectNumber == 0) {
            return rareCharacters.get(random.nextInt(rareCharacters.size()));
        } else {
            return normalCharacters.get(random.nextInt(normalCharacters.size()));
        }
    }

    /*
     * 랜덤하게 퀘스트 타입 선택
     * 0 : 퀴즈 1~N : 게임번호
     * */
    private int randomQuest(int maxCountGame) {
        Random random = new Random();
        return random.nextInt(maxCountGame);
    }

    /*
     * LAT, LNG 좌표 위치에서 MAX_RADIUS 반지름을 가지는 원에서
     * 랜덤한 좌표값을 리턴함
     * LAT, LNG는 구미와 대구 중간 칠곡으로 임의로 정함
     * */
    public LatLng getRandomLocation() {
        double d2r = Math.PI / 180;
        double r2d = 180 / Math.PI;
        double earth_rad = 6378000f; //지구 반지름 근사값(미터 단위)

        double r = new Random().nextInt(MAX_RADIUS) + new Random().nextDouble();
        double rlat = (r / earth_rad) * r2d;
        double rlng = rlat / Math.cos(LAT * d2r);

        double theta = Math.PI * (new Random().nextInt(2) + new Random().nextDouble());
        double y = LNG + (rlng * Math.cos(theta));
        double x = LAT + (rlat * Math.sin(theta));
        return new LatLng(x, y);
    }

    @AllArgsConstructor
    static class LatLng {
        double lat;
        double lng;
    }
}
