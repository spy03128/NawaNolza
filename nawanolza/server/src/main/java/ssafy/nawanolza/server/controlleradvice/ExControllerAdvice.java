package ssafy.nawanolza.server.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.nawanolza.server.domain.exception.*;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CharacterNotFountException.class,
            CollectionNotFountException.class,
            GameRoomNotFoundException.class,
            MemberNotFountException.class,
            NotFoundEntryCodeException.class,
            TokenEmptyException.class})
    public ErrorResult NotFoundExHandler(Exception e) {
        log.error("[Exception Handler] ex", e);
        return ErrorResult.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .className(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult DefaultExHandler(Exception e) {
        log.error("[Exception Handler] ex", e);
        return ErrorResult.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .className(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
}
