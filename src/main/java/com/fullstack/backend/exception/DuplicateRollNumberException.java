package com.fullstack.backend.exception;

public class DuplicateRollNumberException extends RuntimeException {

    public DuplicateRollNumberException(String message) {
        super(message);
    }
}
