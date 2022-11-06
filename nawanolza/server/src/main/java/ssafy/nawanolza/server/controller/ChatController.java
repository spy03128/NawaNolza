package ssafy.nawanolza.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import ssafy.nawanolza.server.domain.socket.dto.ChatDto;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void chatBroadCast(ChatDto request) {
        log.info("ChatDto = {}", request);
        simpMessageSendingOperations.convertAndSend("/sub/chat/" + request.getEntryCode(), request);
    }
}
