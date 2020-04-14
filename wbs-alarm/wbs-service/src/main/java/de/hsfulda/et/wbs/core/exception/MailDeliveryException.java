package de.hsfulda.et.wbs.core.exception;

import java.text.MessageFormat;

public class MailDeliveryException extends RuntimeException {

    private MailDeliveryException() {
    }

    public MailDeliveryException(Throwable ex, String message, Object... params) {
        super(MessageFormat.format(message, params), ex);
    }
}
