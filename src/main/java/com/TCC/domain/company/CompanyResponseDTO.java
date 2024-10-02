package com.TCC.domain.company;

import com.TCC.domain.user.UserResponseDTO;

public record CompanyResponseDTO(
        String id,
        String name,
        String address,
        String phone,
        String duns,
        UserResponseDTO user
) {
}
