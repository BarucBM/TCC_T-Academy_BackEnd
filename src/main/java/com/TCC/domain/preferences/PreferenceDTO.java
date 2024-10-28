package com.TCC.domain.preferences;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record PreferenceDTO(
        @NotBlank
        String notificationType,

        Integer alertFrequency,

        Boolean isActive
) {
}
