package de.hsfulda.et.wbs.core.exception;

import java.text.MessageFormat;

public class TransaktionValidationException extends RuntimeException {

    private TransaktionValidationException() {
    }

    public TransaktionValidationException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }
}
