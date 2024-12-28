package com.abasiemeka.eventsdrivenwalletapp.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserRegistrationDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    String middleName,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password,
    @NotNull @Past LocalDate dateOfBirth,
    @NotBlank String address,
    @NotBlank @Size(min = 11, max = 11) String bvn
) {}

