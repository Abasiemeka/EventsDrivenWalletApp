package com.abasiemeka.eventsdrivenwalletapp.exceptionHandler.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}

