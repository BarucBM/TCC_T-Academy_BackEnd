package com.TCC.domain.customer;

import com.TCC.domain.user.UserDTO;
import jakarta.validation.Valid;

public record CustomerUserDTO(@Valid UserDTO user, @Valid CustomerDTO customer) {
}
