package com.TCC.domain.customer;

import com.TCC.domain.user.UserResponseDTO;

public record CustomerResponseDTO(
        String id,
        String name,
        String address,
        String phone,
        UserResponseDTO user
) {
}
