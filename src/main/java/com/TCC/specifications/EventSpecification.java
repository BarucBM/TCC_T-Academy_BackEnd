package com.TCC.specifications;


import com.TCC.domain.event.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventSpecification {

    public static Specification<Event> titleContains(String search) {
        return ((root, query, criteriaBuilder) ->
                search == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + search.toLowerCase() + "%"));
    }


    public static Specification<Event> descriptionContains(String search) {
        return ((root, query, criteriaBuilder) ->
                search == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + search.toLowerCase() + "%"));
    }

    public static Specification<Event> hasStartTimeBetween(LocalDate firstDate, LocalDate secondDate) {
        if (firstDate != null || secondDate != null) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("startTime"), firstDate, secondDate));
        } else {
            return null;
        }

    }
}
