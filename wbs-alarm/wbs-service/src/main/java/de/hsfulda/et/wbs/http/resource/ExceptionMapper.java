package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<HalJsonResource> resourceNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<HalJsonResource> illegalArgumentException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, AuthorityAlreadyGrantedException.class, BestandAlreadyExistsException.class})
    public final ResponseEntity<HalJsonResource> alreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ZielortLockedException.class)
    public final ResponseEntity<HalJsonResource> zielortLockedException() {
        return new ResponseEntity<>(HttpStatus.LOCKED);
    }
}
