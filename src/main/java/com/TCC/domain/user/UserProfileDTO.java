package com.TCC.domain.user;

import com.TCC.domain.address.Address;
import com.TCC.domain.image.Image;

public record UserProfileDTO(
        String email,
        String name,
        Image image,
        UserRole role,
        Address address
) {
}
