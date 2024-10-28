package com.TCC.domain.company;

import com.TCC.domain.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CompanyDTO(
        @NotBlank String name,
        @Valid AddressDTO address,
        @NotBlank String phone,
        @NotBlank String duns
) {
}
