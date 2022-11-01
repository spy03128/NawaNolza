package ssafy.nawanolza.server.domain.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssafy.nawanolza.server.domain.exception.GameRoomOverflowException;
import ssafy.nawanolza.server.domain.exception.UnderstaffedException;
import ssafy.nawanolza.server.domain.utils.CreateRoomUtil;

import java.time.LocalDateTime;
import java.util.*;

@Getter @Setter
public class HideAndSeekGameRoom extends GameRoom {

    private Map<Long, Role> roles = new HashMap<>();        // 시작할때
    private Map<Long, Boolean> status = new HashMap<>();    // 시작할때
    private HideAndSeekProperties hideAndSeekProperties;    // 방 참가시
    private LocalDateTime startTime;                        // 시작할때
    private List<Long> participants = new ArrayList<>();    // 방 참가시

    private static final int MAXIMUM_PEOPLE_COUNT = 15;


    protected enum Role {
        TAGGER, RUNNER
    }

    @Builder
    private HideAndSeekGameRoom(Long hostId, String entryCode, HideAndSeekProperties hideAndSeekProperties) {
        super(hostId, entryCode);
        this.hideAndSeekProperties = hideAndSeekProperties;
    }

    public static HideAndSeekGameRoom create(Long hostId, HideAndSeekProperties hideAndSeekProperties, CreateRoomUtil createRoomUtil) {
        return HideAndSeekGameRoom.builder()
                .hostId(hostId)
                .entryCode(createRoomUtil.issueEntryCode())
                .hideAndSeekProperties(hideAndSeekProperties)
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
