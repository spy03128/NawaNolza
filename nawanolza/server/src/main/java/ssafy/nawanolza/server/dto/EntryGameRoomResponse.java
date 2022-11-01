package ssafy.nawanolza.server.dto;

import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekGameRoom;

import java.util.List;

public class EntryGameRoomResponse {

    private String message = "방 입장에 성공하였습니다.";
    private long hostId;
    private double lat;
    private double lng;
    private long playTime;
    private long hideTime;
    private int range;

    private List<MemberDto> participants;

    public EntryGameRoomResponse(HideAndSeekGameRoom gameRoom, List<MemberDto> participants) {
        this.hostId = gameRoom.getHostId();
        this.lat = gameRoom.getHideAndSeekProperties().getLat();
        this.lng = gameRoom.getHideAndSeekProperties().getLng();
        this.playTime = gameRoom.getHideAndSeekProperties().getPlayTime();
        this.hideTime = gameRoom.getHideAndSeekProperties().getHideTime();
        this.range = gameRoom.getHideAndSeekProperties().getRange();
        this.participants = participants;
    }
}
