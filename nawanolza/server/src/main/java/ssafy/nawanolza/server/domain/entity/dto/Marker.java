package ssafy.nawanolza.server.domain.entity.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("marker")
public class Marker {

    @Id
    Long markerId;
    Long characterId;
    boolean rare;
    int questType;
    int time;

    @Builder
    public Marker(Long markerId, Long characterId, boolean rare, int questType, int time){
        this.markerId = markerId;
        this.characterId = characterId;
        this.rare = rare;
        this.questType = questType;
        this.time = time;
    }
}
