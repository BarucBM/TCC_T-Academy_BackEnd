package com.TCC.services;

import com.TCC.domain.address.Address;
import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.domain.image.Image;
import com.TCC.repositories.EventRepository;
import com.TCC.specifications.EventSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ImageService imageService;
    private final AddressService addressService;

    public EventService(EventRepository eventRepository, ImageService imageService, AddressService addressService) {
        this.eventRepository = eventRepository;
        this.imageService = imageService;
        this.addressService = addressService;
    }

    public List<Event> getAllEvents(String search, LocalDateTime firsDate, LocalDateTime secondDate) {
        Specification<Event> spec = Specification
                .where(EventSpecification.titleContains(search))
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

    @Transactional
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event();
        BeanUtils.copyProperties(eventDTO, event);

        List<Image> images = new ArrayList<>();

        try {
            for (MultipartFile file : eventDTO.images()) {
               images.add(imageService.uploadImage(file));
            }

            event.setImages(images);

            Address address = addressService.createAddress(eventDTO.address());
            event.setAddress(address);
        } catch (IOException e) {
            System.out.println(e);
        }

        return eventRepository.save(event);
    }

    public Event updateEvent(String id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
        BeanUtils.copyProperties(eventDTO, event);
        return eventRepository.save(event);
    }

}
