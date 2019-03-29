package de.hsfulda.et.wbs.core.exception;

import java.text.MessageFormat;

public class UserAlreadyExistsException extends RuntimeException {

    private UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message, Object... param) {
        super(MessageFormat.format(message, param));
    }
}
