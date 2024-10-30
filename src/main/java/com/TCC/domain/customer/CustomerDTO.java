package com.TCC.domain.customer;

import com.TCC.domain.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank String name,
        @Valid AddressDTO address,
        String phone
) {
}
