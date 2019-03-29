package de.hsfulda.et.wbs.core.exception;

public class BestandAlreadyExistsException extends RuntimeException {

    private BestandAlreadyExistsException() {
    }

    public BestandAlreadyExistsException(String message) {
        super(message);
    }
}
