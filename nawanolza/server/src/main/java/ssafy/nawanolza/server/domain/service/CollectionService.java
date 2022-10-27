package ssafy.nawanolza.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.nawanolza.server.domain.entity.Collection;
import ssafy.nawanolza.server.domain.entity.Member;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.exception.MemberNotFountException;
import ssafy.nawanolza.server.domain.repository.CollectionCustomRepositoryImpl;
import ssafy.nawanolza.server.domain.repository.MapCharacterRedisRepository;
import ssafy.nawanolza.server.domain.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final MemberRepository memberRepository;
    private final CollectionCustomRepositoryImpl collectionCustomRepository;
    private final MapCharacterRedisRepository mapCharacterRedisRepository;

    public List<Collection> getCollection(Long memberId, String type, String sort){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFountException(memberId));

        return collectionCustomRepository.getCollection(memberId, type, sort);
    }

    public List<Marker> getMapCharacters(){
        return (List<Marker>) mapCharacterRedisRepository.findAll();
    }
}