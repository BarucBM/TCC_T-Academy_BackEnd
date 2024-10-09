package com.TCC.domain.event;

import com.TCC.domain.address.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventDTO(
        @NotBlank String title,
        @NotBlank String description,
        AddressDTO address,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean freeEntry,
        BigDecimal ticketUnitPrice,
        BigDecimal ticketTax,
        MultipartFile[] images
) {
}
