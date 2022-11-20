package ssafy.nawanolza.server.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ssafy.nawanolza.server.handler.event.GameEndEvent;
import ssafy.nawanolza.server.handler.event.GameFinishEvent;
import ssafy.nawanolza.server.handler.event.GameStartEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class HideAndSeekEventHandler {

    private final SimpMessageSendingOperations operations;

    @Async
    @EventListener
    public void gameStart(GameStartEvent event) {
        operations.convertAndSend("/sub/game/start/" + event.getEntryCode(), event);
    }

    @Async
    @EventListener
    public void gameFinish(GameFinishEvent event) {
        operations.convertAndSend("/sub/game/finish/" + event.getEntryCode(), event);
    }

    @Async
    @EventListener
    public void gameEnd(GameEndEvent event) {
        operations.convertAndSend("/sub/game/end/" + event.getEntryCode(), event);
    }
}
