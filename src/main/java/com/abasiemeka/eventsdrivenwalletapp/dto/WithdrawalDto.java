package com.abasiemeka.eventsdrivenwalletapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawalDto(
    @NotNull @DecimalMin("0.01") BigDecimal amount
) {}

