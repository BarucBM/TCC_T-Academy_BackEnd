package com.TCC.specifications;

import com.TCC.domain.event.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class EventSpecification {

    public static Specification<Event> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Event> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<Event> isWeatherImpact(Boolean weatherImpact) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("weatherImpact"), weatherImpact);
    }

    public static Specification<Event> isBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("startTime"), startDate, endDate);
    }
}
