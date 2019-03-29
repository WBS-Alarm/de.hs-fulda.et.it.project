package de.hsfulda.et.wbs.core.exception;

public class ZielortLockedException extends RuntimeException {

    private ZielortLockedException() {
    }

    public ZielortLockedException(String message) {
        super(message);
    }
}
