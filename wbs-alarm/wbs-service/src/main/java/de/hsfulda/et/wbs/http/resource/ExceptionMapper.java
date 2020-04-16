package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<HalJsonResource> resourceNotFoundException(Throwable exc) {
        return toResponse(HttpStatus.NOT_FOUND, exc);
    }

    @ExceptionHandler({IllegalStateException.class, MailDeliveryException.class})
    public final ResponseEntity<HalJsonResource> illegalStateException(@AuthenticationPrincipal WbsUser user,
            Throwable exc) {
        LOGGER.error("Unerwarteter Fehler durch {}", user.getUsername());
        LOGGER.error(exc.getMessage(), exc);
        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR, exc);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<HalJsonResource> illegalArgumentException(Throwable exc) {
        return toResponse(HttpStatus.BAD_REQUEST, exc);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, AuthorityAlreadyGrantedException.class, BestandAlreadyExistsException.class})
    public final ResponseEntity<HalJsonResource> alreadyExistsException(Throwable exc) {
        return toResponse(HttpStatus.CONFLICT, exc);
    }

    @ExceptionHandler(ZielortLockedException.class)
    public final ResponseEntity<HalJsonResource> zielortLockedException(Throwable exc) {
        return toResponse(HttpStatus.LOCKED, exc);
    }

    @ExceptionHandler(TransaktionValidationException.class)
    public final ResponseEntity<HalJsonResource> transaktionValidationException(Throwable exc) {
        return toResponse(HttpStatus.BAD_REQUEST, exc);
    }

    private ResponseEntity<HalJsonResource> toResponse(HttpStatus status, Throwable exc) {
        HalJsonResource message = new HalJsonResource();
        message.addProperty("message", exc.getMessage());
        return new ResponseEntity<>(message, status);
    }

}
