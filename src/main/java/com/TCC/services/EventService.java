package com.TCC.services;

import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.repositories.EventRepository;
import com.TCC.specifications.EventSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDate;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public List<Event> getAllEvents(String search, LocalDate firsDate, LocalDate secondDate){
        Specification<Event> spec = Specification
                .where(EventSpecification.titleContains(search))
                .or(EventSpecification.locationContains(search))
                .or(EventSpecification.descriptionContains(search))
                .and(EventSpecification.hasStartTimeBetween(firsDate, secondDate));
        return eventRepository.findAll(spec);

    }

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
    }

    public String deleteEvent(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
        eventRepository.delete(event);
        return event.getTitle() + " event deleted!";
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(String id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
        BeanUtils.copyProperties(eventDTO, event);
        return eventRepository.save(event);
    }

    public List<Event> searchEvents(String title, String location, Boolean weatherImpact, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<Event> spec = Specification.where(null);

        if (StringUtils.hasText(title)) {
            spec = spec.and(EventSpecification.hasTitle(title));
        }

        if (StringUtils.hasText(location)) {
            spec = spec.and(EventSpecification.hasLocation(location));
        }

        if (weatherImpact != null) {
            spec = spec.and(EventSpecification.isWeatherImpact(weatherImpact));
        }

        if (startDate != null && endDate != null) {
            spec = spec.and(EventSpecification.isBetweenDates(startDate, endDate));
        }

        return eventRepository.findAll(spec);
    }
}
