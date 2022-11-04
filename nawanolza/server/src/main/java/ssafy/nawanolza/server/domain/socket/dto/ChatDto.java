package ssafy.nawanolza.server.domain.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    @NotNull
    private Long senderId;

    @NotNull
    private String senderImage;

    @NotNull
    private Long roomId;

    @NotBlank
    private String message;
}
