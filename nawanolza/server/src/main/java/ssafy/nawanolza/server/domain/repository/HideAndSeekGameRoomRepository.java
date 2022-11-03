package ssafy.nawanolza.server.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;

public interface HideAndSeekGameRoomRepository extends CrudRepository<HideAndSeekGameRoom, String> {
    HideAndSeekGameRoom findByEntryCode(String entryCode);
}
