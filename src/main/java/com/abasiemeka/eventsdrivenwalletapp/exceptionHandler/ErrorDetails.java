package com.abasiemeka.eventsdrivenwalletapp.exceptionHandler;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details) {}

