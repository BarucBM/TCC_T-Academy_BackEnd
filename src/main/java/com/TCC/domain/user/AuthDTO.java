package com.TCC.domain.user;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank String email, @NotBlank String password) {
}
