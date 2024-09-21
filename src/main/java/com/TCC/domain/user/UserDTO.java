package com.TCC.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String email,
        @NotBlank String password,
        String googleApiToken,
        UserRole role
) {
}
