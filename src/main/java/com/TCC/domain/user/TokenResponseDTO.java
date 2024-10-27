package com.TCC.domain.user;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String token, String refreshToken) {
}
