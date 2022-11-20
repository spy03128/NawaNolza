package ssafy.nawanolza.server.domain.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatDto extends GameRoomBaseDTO{

    @NotNull
    private String senderImage;

    @NotNull
    private String senderName;

    @NotBlank
    private String message;

    private LocalDateTime chatTime;

    public ChatDto() {
        super(Type.CHAT);
    }
}
