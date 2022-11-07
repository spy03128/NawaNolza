package ssafy.nawanolza.server.domain.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import ssafy.nawanolza.server.domain.exception.GameRoomOverflowException;
import ssafy.nawanolza.server.domain.exception.UnderstaffedException;
import java.time.LocalDateTime;
import java.util.*;

@Getter @Setter
@RedisHash("HideAndSeekGameRoom")
@ToString
public class HideAndSeekGameRoom {

    @Id
    private String entryCode;
    private Long hostId;
    private Map<Long, Role> roles = new HashMap<>();        // 시작할때
    private Map<Long, Boolean> status = new HashMap<>();    // 시작할때
    private HideAndSeekProperties hideAndSeekProperties;    // 방 참가시
    private LocalDateTime startTime;                        // 시작할때
    private List<Long> participants = new ArrayList<>();    // 방 참가시
    private double lat;
    private double lng;
    private int range;

    private static final int MAXIMUM_PEOPLE_COUNT = 15;


    protected enum Role {
        TAGGER, RUNNER
    }

    @Builder
    private HideAndSeekGameRoom(Long hostId, String entryCode, HideAndSeekProperties hideAndSeekProperties,
                                double lat, double lng, int range) {
        this.hostId = hostId;
        this.entryCode = entryCode;
        this.hideAndSeekProperties = hideAndSeekProperties;
        this.lat = lat;
        this.lng = lng;
        this.range = range;
        participants.add(hostId);
    }

    public static HideAndSeekGameRoom create(Long hostId, HideAndSeekProperties hideAndSeekProperties, String entryCode) {
        return HideAndSeekGameRoom.builder()
                .hostId(hostId)
                .entryCode(entryCode)
                .hideAndSeekProperties(hideAndSeekProperties)
                .lat(hideAndSeekProperties.getLat())
                .lng(hideAndSeekProperties.getLng())
                .range(hideAndSeekProperties.getRange())
                .build();
    }

    public HideAndSeekGameRoom participateMember(Long memberId) {
        if (participants.size() > MAXIMUM_PEOPLE_COUNT)
            throw new GameRoomOverflowException();
        participants.add(memberId);
        return this;
    }

    public Map<String, Object> startGame() {
        if (participants.size() < 2)    throw new UnderstaffedException(participants.size());
        participants.stream().forEach(id -> status.put(id, false));
        Map<String, Object> returnMap = assignRoles(participants);
        startTime = LocalDateTime.now();
        return returnMap;
    }

    private void designateTagger(List<Long> participants, HashMap<String, Object> returnMap) {
        Collections.shuffle(participants);
        Long tagger = participants.remove(participants.size() - 1);
        roles.put(tagger, Role.TAGGER);
        status.put(tagger, true);
        returnMap.put("tagger", tagger);
    }

    private void designateRunner(List<Long> participants, HashMap<String, Object> returnMap) {
        returnMap.put("runners", new ArrayList<>());
        participants.parallelStream().forEach((id) -> {
            roles.put(id, Role.RUNNER);
            ((List) returnMap.get("runners")).add(id);
        });
    }

    private Map<String, Object> assignRoles(List<Long> participants) {
        HashMap<String, Object> returnMap = new HashMap<>();
        designateTagger(participants, returnMap);
        designateRunner(participants, returnMap);
        return returnMap;
    }

    public void seekRunner(Long seekRunnerId) {
        status.put(seekRunnerId, true);
    }
}
