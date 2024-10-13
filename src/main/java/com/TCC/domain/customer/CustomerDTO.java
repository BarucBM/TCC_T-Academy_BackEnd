package com.TCC.domain.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String phone
) {
}