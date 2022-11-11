package ssafy.nawanolza.server.handler.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkerCreateEvent {
    private String message;
}
