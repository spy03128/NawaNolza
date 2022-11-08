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
}
