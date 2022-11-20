package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.nawanolza.server.domain.entity.Character;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.History;
import ssafy.nawanolza.server.domain.entity.Member;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.exception.CharacterNotFountException;
import ssafy.nawanolza.server.domain.exception.MemberNotFountException;
import ssafy.nawanolza.server.domain.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectionService {
    private final MemberRepository memberRepository;
    private final CollectionCharacterRepository collectionCharacterRepository;
    private final CollectionRepository collectionRepository;
    private final TypeRepository typeRepository;
    private final CharacterTypeRepository characterTypeRepository;
    private final HistoryRepository historyRepository;
    private final MapCharacterRedisRepository mapCharacterRedisRepository;
    private final CharacterRepository characterRepository;

    @Transactional
    public Collection saveCollection(Long memberId, Long characterId) {
        Collection findCollection = collectionRepository.findByMemberIdAndCharacterId(memberId, characterId).orElse(null);

        if (findCollection != null) {
            findCollection.levelUp();
        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFountException(memberId));
            Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFountException(characterId));
            findCollection = collectionRepository.save(new Collection(member, character));
        }

        return findCollection;
    }


    public List<CollectionCharacterRepository.CollectionCharacterDto> getCollection(Long memberId, String type, String sort){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFountException(memberId));

        boolean filterType = type == null || type.equals("전체") || type.equals("");

        if(sort!=null && sort.equals("level")){
            if(filterType){
                return collectionCharacterRepository.findAllSortByLevel(memberId);
            }
            return collectionCharacterRepository.findAllSortByLevelFilterByType(type, memberId);

        }else{
            if(filterType){
                return collectionCharacterRepository.findAllSortByCharacterId(memberId);
            }
            return collectionCharacterRepository.findAllSortByCharacterIdFilterByType(type, memberId);

        }
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


}
