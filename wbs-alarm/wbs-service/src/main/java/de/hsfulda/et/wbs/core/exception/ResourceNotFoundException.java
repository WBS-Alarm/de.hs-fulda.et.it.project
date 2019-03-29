package de.hsfulda.et.wbs.core.exception;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {

    private ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }
}
