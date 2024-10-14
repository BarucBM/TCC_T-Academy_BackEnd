package com.TCC.services;

import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.domain.image.Image;
import com.TCC.repositories.EventRepository;
import com.TCC.specifications.EventSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + id));
    }

    @Transactional
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event();
        BeanUtils.copyProperties(eventDTO, event);

        event.setAddress(addressService.createAddress(eventDTO.address()));
        this.uploadImages(event, eventDTO.images());

        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(String id, EventDTO eventDTO) {
        Event event = this.getEventById(id);
        BeanUtils.copyProperties(eventDTO, event);

        this.deleteImages(event);
        this.uploadImages(event, eventDTO.images());

        event.setAddress(addressService.updateAddress(event.getAddress().getId(), eventDTO.address()));

        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(String id) {
        Event event = this.getEventById(id);

        this.deleteImages(event);
        addressService.deleteAddress(event.getAddress());

        eventRepository.delete(event);
    }

    public void uploadImages(Event event, MultipartFile[] files) {
        List<Image> images = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                images.add(imageService.uploadImage(file));
            }
        }

        event.setImages(images);
    }

    public void deleteImages(Event event) {
        List<Image> oldImages = event.getImages();
        event.setImages(new ArrayList<>());

        for (Image image : oldImages) {
            imageService.deleteImage(image);
        }
    }
}
