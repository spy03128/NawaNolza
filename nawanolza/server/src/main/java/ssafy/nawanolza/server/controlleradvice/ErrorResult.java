package ssafy.nawanolza.server.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResult {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
