package de.hsfulda.et.wbs.core.exception;

public class ZielortDeletionException extends RuntimeException {

    private ZielortDeletionException() {
    }

    public ZielortDeletionException(String message) {
        super(message);
    }
}
