package com.TCC.domain.event;

import com.TCC.domain.address.Address;
import com.TCC.domain.company.Company;
import com.TCC.domain.image.Image;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CustomerEventDTO(
        String id,
        String title,
        String description,
        Address address,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean freeEntry,
        BigDecimal ticketUnitPrice,
        BigDecimal ticketTax,
        List<Image> images,
        Company company,
        LocalDateTime acquisitionDate,
        int customerRating
) {
}
