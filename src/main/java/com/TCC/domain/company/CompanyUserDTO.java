package com.TCC.domain.company;

import com.TCC.domain.user.UserDTO;
import jakarta.validation.Valid;

public record CompanyUserDTO(@Valid UserDTO user, @Valid CompanyDTO company) {
}
