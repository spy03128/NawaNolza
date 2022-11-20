package ssafy.nawanolza.server.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ssafy.nawanolza.server.domain.entity.dto.Marker;
import ssafy.nawanolza.server.domain.repository.CollectionCharacterRepository;
import ssafy.nawanolza.server.handler.event.MarkerCreateEvent;
import ssafy.nawanolza.server.handler.event.MarkerRemoveEvent;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CollectionEventHandler {

    private final SimpMessageSendingOperations operations;

    @Async
    @EventListener
    public void makerRemove(MarkerRemoveEvent event) {
        operations.convertAndSend("/sub/collection", event);
    }

    @EventListener
    public void markerCreate(MarkerCreateEvent event) {
        System.out.println(event.getMessage());

        operations.convertAndSend("/sub/update", event);
    }
}
