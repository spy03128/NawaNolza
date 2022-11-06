package ssafy.nawanolza.server.api;

import lombok.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.History;
import ssafy.nawanolza.server.domain.entity.Quiz;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.repository.CollectionCharacterRepository;
import ssafy.nawanolza.server.domain.service.CollectionService;
import ssafy.nawanolza.server.domain.service.MarkerService;
import ssafy.nawanolza.server.domain.service.QuizService;
import ssafy.nawanolza.server.dto.ResponseDto;
import ssafy.nawanolza.server.handler.event.MarkerRemoveEvent;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collection")
@AllArgsConstructor
public class CollectionApiController {

    private final CollectionService collectionService;
    private final MarkerService markerService;
    private final QuizService quizService;
    private final ApplicationEventPublisher publisher;


    @GetMapping("/{memberId}")
    public ResponseEntity<CollectionResponseDto> getCollection(@PathVariable Long memberId,
                                                               @RequestParam(name = "type",required = false) String type,
                                                               @RequestParam(name = "sort",required = false) String sort){
        return ResponseEntity.ok(CollectionResponseDto.of(collectionService.getCollection(memberId, type, sort)));
    }

    @GetMapping("/{memberId}/detail/{characterId}")
    public ResponseEntity<CharacterDetailResponseDto> getCharacterDetail(@PathVariable Long memberId, @PathVariable Long characterId){
        Character character = collectionService.getCharacterDetail(characterId);
        Collection collection = collectionService.getCollectionDetail(memberId, characterId);

        return ResponseEntity.ok(CharacterDetailResponseDto.of(
                character,
                collection,
                collectionService.getTypesDetail(character),
                collectionService.getHistoryDetail(collection)));
    }

    @GetMapping("/map")
    public ResponseEntity<List<Marker>> getMapCharacters(){
        return ResponseEntity.ok(collectionService.getMapCharacters());
    }

    /*
     * 테스트용 마커 생성 API -> 개발 완료시 삭제해야함
     * */
    @PostMapping("/marker")
    public void createMarker(){
        markerService.insertMarker();
    }

    @PostMapping("/quest/start")
    public ResponseEntity<QuestResponseDto> startQuest(@Valid @RequestBody MarkerRequestDto marker){
        boolean isLock = markerService.questStart(marker.getMarkerId());
        Quiz quiz = isLock ? quizService.getQuiz(marker.questType) : null;
        return ResponseEntity.ok(QuestResponseDto.builder().accessible(isLock).quiz(quiz).build());
    }

    @PostMapping("/quest/success")
    public ResponseEntity<QuestSuccessResponse> questSuccess(@RequestBody QuestSuccessDto marker) throws InterruptedException {
        markerService.questSuccess(marker.getMarkerId());
        Collection collection = collectionService.saveCollection(marker.getMemberId(), marker.getCharacterId());
        publisher.publishEvent(new MarkerRemoveEvent(marker.getMarkerId()));
        return ResponseEntity.ok().body(new QuestSuccessResponse(collection));
    }

    @PostMapping("/quest/fail")
    public boolean questFail(@RequestBody MarkerRequestDto marker){
        return markerService.questFail(marker.getMarkerId());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionResponseDto{
        List<CollectionCharacterRepository.CollectionCharacterDto> collection = new ArrayList<>();

        public static CollectionResponseDto of(List<CollectionCharacterRepository.CollectionCharacterDto> collections){
            return new CollectionResponseDto(collections);
        }

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CharacterDetailResponseDto{
        String name;
        String description;
        boolean rare;
        int level;
        List<String> type;
        List<Histories> history;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Histories{
            String createdAt;
            int level;
        }

        public static CharacterDetailResponseDto of(Character character, Collection collection, List<String> type, List<History> historyList){
            List<Histories> histories = new ArrayList<>();
            historyList.forEach(s -> histories.add(new Histories(String.valueOf(s.getCreatedAt()).substring(0,10), s.getLevel())));
            return CharacterDetailResponseDto.builder()
                    .name(character.getName())
                    .description(character.getDescription())
                    .rare(character.isRare())
                    .level(collection != null ? collection.getCurrentLevel() : 0)
                    .type(type)
                    .history(histories).build();
        }
    }

    @Getter
    private static class MarkerRequestDto{
        @NotNull
        private Long markerId;
        @NotNull
        private int questType;
    }

    @Builder
    @Data
    private static class QuestResponseDto {
        private boolean accessible;
        private Quiz quiz;
    }

    @Getter
    private static class QuestSuccessDto {
        @NotNull
        private Long memberId;
        @NotNull
        private Long markerId;
        @NotNull
        private Long characterId;
    }

    @Getter
    private class QuestSuccessResponse extends ResponseDto {

        private boolean isSuccess = true;
        private Long getCharacterId;
        private String characterName;

        public QuestSuccessResponse(Collection collection) {
            super("퀘스트를 완료했습니다.");
            this.getCharacterId = collection.getCharacter().getId();
            this.characterName = collection.getCharacter().getName();
        }
    }
}
