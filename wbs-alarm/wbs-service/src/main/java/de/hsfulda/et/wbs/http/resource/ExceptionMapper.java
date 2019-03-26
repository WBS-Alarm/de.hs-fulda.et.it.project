package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.AuthorityAlreadyGrantedException;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.UserAlreadyExistsException;
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

    @ExceptionHandler({UserAlreadyExistsException.class, AuthorityAlreadyGrantedException.class})
    public final ResponseEntity<HalJsonResource> userAlreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
