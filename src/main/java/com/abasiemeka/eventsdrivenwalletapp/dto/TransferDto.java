package com.abasiemeka.eventsdrivenwalletapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferDto(
    @Email String recipientEmail,
    @NotNull @DecimalMin("0.01") BigDecimal amount
) {}

