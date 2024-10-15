package com.TCC.domain.cart;

import jakarta.validation.constraints.NotBlank;

public record CartItemDTO(
        @NotBlank String eventId,
        @NotBlank Integer quantity
) { }
