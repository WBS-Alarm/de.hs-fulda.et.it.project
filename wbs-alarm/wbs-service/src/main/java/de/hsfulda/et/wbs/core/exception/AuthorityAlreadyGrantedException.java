package de.hsfulda.et.wbs.core.exception;

public class AuthorityAlreadyGrantedException extends RuntimeException {

    private AuthorityAlreadyGrantedException() {
    }

    public AuthorityAlreadyGrantedException(String message) {
        super(message);
    }
}
