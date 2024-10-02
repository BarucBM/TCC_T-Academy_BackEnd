package com.TCC.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(String id, @NotBlank String email, @NotBlank String googleApiToken) {
}
