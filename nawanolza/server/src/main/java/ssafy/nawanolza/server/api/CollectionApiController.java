package ssafy.nawanolza.server.api;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.History;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.service.CollectionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collection")
@AllArgsConstructor
public class CollectionApiController {
    private final CollectionService collectionService;

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
    public ResponseEntity<List<Marker>> getMapCharacters(@ModelAttribute MapCharactersRequest mapCharactersRequest){
        return ResponseEntity.ok(collectionService.getMapCharacters());
    }

    /*
     * 테스트용 마커 생성 API -> 개발 완료시 삭제해야함
     * */
    @PostMapping("/marker")
    public void createMarker(){
        collectionService.makeMarker();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionResponseDto{
        List<CollectionItems> collection = new ArrayList<>();

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CollectionItems{
            Long characterId;
            boolean rare;
            int level;
        }

        public static CollectionResponseDto of(List<Collection> collections){
            CollectionResponseDto collectionResponseDto = new CollectionResponseDto();
            for(int i=0; i<collections.size(); i++){
                Collection collection = collections.get(i);
                collectionResponseDto.getCollection().add(new CollectionItems(collection.getCharacter().getId(), collection.getCharacter().isRare(), collection.getCurrentLevel()));
            }
            return collectionResponseDto;
        }

    }
    @Getter
    @Setter
    public static class  MapCharactersRequest{
        double lat;
        double lng;
        int level;
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
                    .level(collection.getCurrentLevel())
                    .type(type)
                    .history(histories).build();
        }
    }
}
