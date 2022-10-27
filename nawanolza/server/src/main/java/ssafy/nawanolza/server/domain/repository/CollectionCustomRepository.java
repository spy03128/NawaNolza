package ssafy.nawanolza.server.domain.repository;

import ssafy.nawanolza.server.domain.entity.Collection;

import java.util.List;

public interface CollectionCustomRepository{
    public List<Collection> getCollection(Long memberId, String type, String sort);
}
