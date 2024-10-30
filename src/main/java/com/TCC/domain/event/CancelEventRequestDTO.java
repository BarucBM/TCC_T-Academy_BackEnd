package com.TCC.domain.event;

import jakarta.validation.constraints.NotBlank;

public record CancelEventRequestDTO (@NotBlank String userId, @NotBlank String eventId) {

}