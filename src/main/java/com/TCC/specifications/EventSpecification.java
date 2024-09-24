package com.TCC.specifications;


import com.TCC.domain.event.Event;
import org.springframework.data.jpa.domain.Specification;

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

}
