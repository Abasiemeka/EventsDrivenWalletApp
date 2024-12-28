package com.abasiemeka.eventsdrivenwalletapp.exceptionHandler.exception;

import org.springframework.web.context.request.WebRequest;

public class illegalArgumentExceptionHandler extends RuntimeException {
    public illegalArgumentExceptionHandler(String message) {
        super(message);
    }
}