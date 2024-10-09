package com.TCC.domain.address;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank String fullAddress,
        @NotBlank String postalCode,
        @NotBlank String streetName,
        String streetNumber,
        String addressComplement,
        String neighborhood,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String latitude,
        @NotBlank String longitude
) {
}
