package ssafy.nawanolza.server.dto;

import lombok.Data;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;
import ssafy.nawanolza.server.domain.repository.HideAndSeekGameRoomRepository;
import ssafy.nawanolza.server.domain.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class HideAndSeekGameRoomResponse extends ResponseDto {

    private Long hostId;
    private String entryCode;
    private HideAndSeekProperties hideAndSeekProperties;
    private List<MemberDto> participants;

    private HideAndSeekGameRoomResponse(String message, HideAndSeekGameRoom gameRoom, MemberRepository repository) {
        super(message);
        this.hostId = gameRoom.getHostId();
        this.entryCode = gameRoom.getEntryCode();
        this.hideAndSeekProperties = gameRoom.getHideAndSeekProperties();
        this.participants = repository.findAllByMemberId(gameRoom.getParticipants().stream().filter(x -> !x.equals(gameRoom.getHostId())).collect(Collectors.toList())).stream().map(MemberDto::new).collect(Collectors.toList());
    }

    public static HideAndSeekGameRoomResponse makeEntryResponse(HideAndSeekGameRoom gameRoom, MemberRepository memberRepository) {
        return new HideAndSeekGameRoomResponse("방 입장에 성공하였습니다.", gameRoom, memberRepository);
    }

    public static HideAndSeekGameRoomResponse makeCreateResponse(HideAndSeekGameRoom gameRoom, MemberRepository memberRepository) {
        return new HideAndSeekGameRoomResponse("방 생성에 성공하엿습니다.", gameRoom, memberRepository);
    }
}
