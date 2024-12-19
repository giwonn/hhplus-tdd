package io.hhplus.tdd.common.exception;

public abstract class CustomException extends RuntimeException {
    protected CustomException(String message) {
        super(message);
    }
}
