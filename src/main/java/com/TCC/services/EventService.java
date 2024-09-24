package com.TCC.services;

import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.repositories.EventRepository;
import com.TCC.specifications.EventSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents(String title, String location, String description){
        Specification<Event> spec = Specification
                .where(EventSpecification.titleContains(title))
                .and(EventSpecification.locationContains(location))
                .and(EventSpecification.descriptionContains(description));
        return eventRepository.findAll(spec);
    }

    public Event getEventById (String id){
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
    }

    public String deleteEvent (String id){
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found!"));
        eventRepository.delete(event);
        return event.getTitle() + " event deleted!";
    }

    public Event createEvent (Event event){
        return eventRepository.save(event);
    }

    public Event updateEvent (String id, EventDTO eventDTO){
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found!"));
        BeanUtils.copyProperties(eventDTO, event);
        return eventRepository.save(event);
    }
}
