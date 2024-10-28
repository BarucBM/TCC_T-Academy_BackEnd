package com.TCC.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String email,
        String password,
        Boolean hasGoogleAuth,
        UserRole role
) {
}
