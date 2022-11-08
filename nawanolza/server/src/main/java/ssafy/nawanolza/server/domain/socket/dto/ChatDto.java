package ssafy.nawanolza.server.domain.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ChatDto extends GameRoomBaseDTO{

    @NotNull
    private String senderImage;

    @NotNull
    private String senderName;

    @NotBlank
    private String message;

    public ChatDto() {
        super(Type.CHAT);
    }
}
