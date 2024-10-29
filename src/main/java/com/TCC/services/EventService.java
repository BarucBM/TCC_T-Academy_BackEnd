package com.TCC.services;

import com.TCC.domain.address.Address;
import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.domain.image.Image;
import com.TCC.repositories.EventRepository;
import com.TCC.specifications.EventSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private GeminiAIService geminiApiClient;

    private final EventRepository eventRepository;
    private final ImageService imageService;
    private final AddressService addressService;

    @Autowired
    public EventService(EventRepository eventRepository, ImageService imageService, AddressService addressService) {
        this.eventRepository = eventRepository;
        this.imageService = imageService;
        this.addressService = addressService;
    }

    public List<Event> getAllEvents(String search, LocalDate firstDate, LocalDate secondDate) {
        Specification<Event> spec = Specification
                .where(EventSpecification.titleContains(search))
                .or(EventSpecification.descriptionContains(search))
                .and(EventSpecification.hasStartTimeBetween(firstDate, secondDate));
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

    public List<Event> findEventsNearLocation(double latitude, double longitude, double radius) {
        Point location = new Point(latitude, longitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        return eventRepository.findEventsNearLocation(latitude, longitude, radius);
    }

    public List<String> suggestEventChanges(Event event, String weatherData) {
        List<String> suggestions = new ArrayList<>();

        if (weatherData.contains("rain")) {
            suggestions.add("Sugira alteração do local para um ambiente coberto para o evento " + event.getTitle());
        }
        if (weatherData.contains("heat")) {
            suggestions.add("Considere alterar o horário para um período mais fresco do dia para o evento " + event.getTitle());
        }
        if (weatherData.contains("cold")) {
            suggestions.add("Recomende que os participantes venham agasalhados para o evento " + event.getTitle());
        }

        return suggestions;
    }

    // Método para obter eventos próximos do usuário
    public List<Event> getNearbyEvents(double userLatitude, double userLongitude, double radius) {
        return eventRepository.findEventsNearLocation(userLatitude, userLongitude, radius);
    }

    public String getNearbyEventsForPrompt(double userLatitude, double userLongitude, double radiusInKm) throws IOException, InterruptedException {
        List<Event> nearbyEvents = eventRepository.findEventsNearLocation(userLatitude, userLongitude, radiusInKm);
        String prompt = formatNearbyEventsForPrompt(nearbyEvents);
        return geminiApiClient.getSuggestions(prompt);
    }


    private String formatNearbyEventsForPrompt(List<Event> events) {
        StringBuilder prompt = new StringBuilder("Upcoming events near you:\n");
        for (Event event : events) {
            prompt.append("Title: ").append(event.getTitle()).append("\n")
                    .append("Description: ").append(event.getDescription()).append("\n")
                    .append("Starts: ").append(event.getStartTime()).append("\n")
                    .append("Ends: ").append(event.getEndTime()).append("\n\n");
        }
        return prompt.toString();
    }

}
