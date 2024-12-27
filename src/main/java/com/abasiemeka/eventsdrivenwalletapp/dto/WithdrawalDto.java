package com.abasiemeka.eventsdrivenwalletapp.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawalDto(
    @NotNull @DecimalMin("0.01") BigDecimal amount
) {}

