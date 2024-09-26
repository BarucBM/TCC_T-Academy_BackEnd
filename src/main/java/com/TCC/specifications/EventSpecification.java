package com.TCC.specifications;


import com.TCC.domain.event.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventSpecification {

    public static Specification<Event> titleContains(String title){
        return ((root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%" +title.toLowerCase()+ "%"));
    }

    public static Specification<Event> locationContains(String location){
        return ((root, query, criteriaBuilder) ->
                location == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("location")),"%" + location.toLowerCase() + "%"));
    }

    public static Specification<Event> descriptionContains(String description){
        return ((root, query, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),"%" + description.toLowerCase() + "%"));
    }

    public static Specification<Event> hasStartTimeBetween(LocalDate firstDate, LocalDate secondDate) {


        if (firstDate != null || secondDate!= null ){
            LocalDateTime startDate = firstDate.atStartOfDay();
            LocalDateTime endDate = secondDate.atTime(23,59,59);
            return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("startTime"),startDate, endDate ));
        } else {
            return null;
        }
    }
}
