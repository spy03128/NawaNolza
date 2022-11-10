package ssafy.nawanolza.server.handler.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ssafy.nawanolza.server.domain.socket.dto.GameEventDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GameStartEvent {
    private String entryCode;
    private Long tagger;
    private List<Long> runners;
    private LocalDateTime startTime;
    private long playTime;
    private double lat;
    private double lng;
    private int range;


    public GameStartEvent() {
        this.entryCode = entryCode;
        this.tagger = tagger;
        this.runners = runners;
        this.startTime = startTime;
        this.playTime = playTime;
        this.lat = lat;
        this.lng = lng;
        this.range = range;
    }

    public static GameStartEvent of(String entryCode, Long tagger, List<Long> runners, LocalDateTime startTime,
                                    long playTime, double lat, double lng, int range) {
        return new GameStartEvent(entryCode, tagger, runners, startTime, playTime, lat, lng, range);
    }
}
