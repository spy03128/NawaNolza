package ssafy.nawanolza.server.api;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.nawanolza.server.domain.entity.Collection;
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

    @GetMapping("/map")
    public ResponseEntity<List<Marker>> getMapCharacters(@ModelAttribute MapCharactersRequest mapCharactersRequest){
        return ResponseEntity.ok(collectionService.getMapCharacters());
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
}
