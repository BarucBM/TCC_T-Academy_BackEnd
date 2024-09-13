package com.TCC.domain.event;

import java.time.LocalDateTime;

public record EventDTO(
        String title,

        LocalDateTime startTime,

        LocalDateTime endTime,

        String location,

        String description,

        Boolean weatherImpact
) {
}
