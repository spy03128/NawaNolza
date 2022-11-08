package ssafy.nawanolza.server.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ssafy.nawanolza.server.handler.event.GameStartEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class HideAndSeekEventHandler {

    private final SimpMessageSendingOperations operations;

    @Async
    @EventListener
    public void makerRemove(GameStartEvent event) {
        operations.convertAndSend("/sub/game/start/" + event.getEntryCode(), event);
    }
}
