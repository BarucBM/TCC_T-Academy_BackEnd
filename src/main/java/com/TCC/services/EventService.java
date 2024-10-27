package com.TCC.services;

import com.TCC.domain.company.Company;
import com.TCC.domain.event.CustomerEventDTO;
import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.domain.image.Image;
import com.TCC.domain.user.UserEvent;
import com.TCC.repositories.EventRepository;
import com.TCC.repositories.UserEventRepository;
import com.TCC.specifications.EventSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserEventRepository userEventRepository;
    private final ImageService imageService;
    private final AddressService addressService;
    private final CompanyService companyService;

    public EventService(EventRepository eventRepository, UserEventRepository userEventRepository, ImageService imageService, AddressService addressService, CompanyService companyService) {
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
        this.imageService = imageService;
        this.addressService = addressService;
        this.companyService = companyService;
    }

    public List<Event> getAllEvents(String search, LocalDate firsDate, LocalDate secondDate) {
        Specification<Event> spec = Specification
                .where(EventSpecification.titleContains(search))
                .or(EventSpecification.descriptionContains(search))
                .and(EventSpecification.hasStartTimeBetween(firsDate, secondDate));
        return eventRepository.findAll(spec);

    }

    public List<CustomerEventDTO> getEventsByUserId(String userId) {
        List<UserEvent> userEvents = userEventRepository.findAllByUserId(userId);

        return userEvents.stream().map(userEvent -> {
            Event event = userEvent.getEvent();
            return new CustomerEventDTO(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getAddress(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getFreeEntry(),
                    event.getTicketUnitPrice(),
                    event.getTicketTax(),
                    event.getImages(),
                    event.getCompany(),
                    userEvent.getAcquisitionDate(),
                    userEvent.getCustomerRating()
            );
        }).toList();
    }

    public List<Event> getEventsOfUserCompany(String userId) {
        return eventRepository.findAllByCompanyId(companyService.findCompanyByUserId(userId).getId());
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + id));
    }

    @Transactional
    public Event createEvent(EventDTO eventDTO, String userId) {
        Event event = new Event();
        BeanUtils.copyProperties(eventDTO, event);

        event.setAddress(addressService.createAddress(eventDTO.address()));
        this.uploadImages(event, eventDTO.images());

        Company company = companyService.findCompanyByUserId(userId);
        event.setCompany(company);

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

    public void rateEvent(String id, int rate) {
        UserEvent userEvent = userEventRepository.findByEventId(id);

        if (userEvent != null) {
            userEvent.setCustomerRating(rate);
            userEventRepository.save(userEvent);
        } else {
            throw new EntityNotFoundException("Event not found with ID: " + id);
        }
    }

    public void deleteUserEvent(String userId, String eventId) {
        UserEvent userEvent = userEventRepository.findByEventIdAndUserId(eventId, userId);

        if (userEvent != null) {
            userEventRepository.delete(userEvent);
        } else {
            throw new EntityNotFoundException("Event not found with ID: " + userId);
        }
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
