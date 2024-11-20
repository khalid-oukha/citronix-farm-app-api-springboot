package com.citronix.api.web.exception;

public class OutOfSpaceException extends RuntimeException {
    public OutOfSpaceException(String message) {
        super(message);
    }
}
