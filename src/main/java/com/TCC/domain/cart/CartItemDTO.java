package com.TCC.domain.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartItemDTO(
        @NotBlank String eventId,
        @NotNull Integer quantity
) { }
