package com.abasiemeka.eventsdrivenwalletapp.dto;

import com.abasiemeka.eventsdrivenwalletapp.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryDto(
		Long id,
		@NotNull @DecimalMin("0.01")
		BigDecimal amount,
		TransactionType type,
		LocalDateTime timestamp,
		String description) {}