package com.TCC.domain.company;

import jakarta.validation.constraints.NotBlank;

public record CompanyDTO(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String phone,
        @NotBlank String duns
) {
}
