package com.TCC.domain.customer;

import com.TCC.domain.address.Address;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank
        String name,

        String address,

        String phone
) {
}
