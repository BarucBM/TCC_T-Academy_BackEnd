package com.TCC.services;

import com.TCC.domain.calendar.CalendarId;
import com.TCC.domain.calendar.TokenRequest;
import com.TCC.domain.company.Company;
import com.TCC.domain.event.CalendarEventDTO;
import com.TCC.domain.event.CustomerEventDTO;
import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.domain.image.Image;
import com.TCC.domain.user.UserEvent;
import com.TCC.repositories.EventRepository;
import com.TCC.repositories.UserEventRepository;
import com.TCC.repositories.UserRepository;
import com.TCC.specifications.EventSpecification;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserEventRepository userEventRepository, ImageService imageService, AddressService addressService, CompanyService companyService, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
        this.imageService = imageService;
        this.addressService = addressService;
        this.companyService = companyService;
        this.userRepository = userRepository;
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

    public void createUserEvent(String userId, String eventId){
        List<UserEvent> userEvents = userEventRepository.findByEventIdAndUserId(eventId, userId);
        if(userEvents.isEmpty()){
            UserEvent userEvent = new UserEvent();
            userEvent.setUser(userRepository.findById(userId)
                    .orElseThrow(()-> new RuntimeException("User not found!")));

            userEvent.setEvent(eventRepository.findById(eventId)
                    .orElseThrow(()-> new RuntimeException("Event not found!")));

            userEventRepository.save(userEvent);
        }else{
            System.out.println("Event already added!");
        }

    }

    public void deleteUserEvent(String userId, String eventId) {
        List<UserEvent> userEvent = userEventRepository.findByEventIdAndUserId(eventId, userId);

        if (!userEvent.isEmpty()) {
            for (UserEvent event: userEvent){
                userEventRepository.delete(event);
            }
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

    public void addEventToCalendar(TokenRequest tokenRequest, CalendarId calendarId, String eventId) throws IOException {
        String calendarApiUrl = "https://www.googleapis.com/calendar/v3/calendars/" + calendarId.getId() + "/events";
        String authorizationToken = tokenRequest.getToken();
        URL url = new URL(calendarApiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Authorization", "Bearer " + authorizationToken);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Event event = getEventById(eventId);

        LocalDate startDate = event.getStartTime().toLocalDate();
        LocalDate endDate = event.getEndTime().toLocalDate();
        String title = event.getTitle();

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null");
        }

        CalendarEventDTO eventDto = new CalendarEventDTO(startDate, endDate, title);

        Gson gson = GsonConfig.createGson();
        String jsonInputString = gson.toJson(eventDto);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        }

        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Body: " + response.toString());

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Event added successfully.");
        } else {
            System.out.println("Failed to add event: " + response.toString());
            throw new IOException("Failed to add event: " + response.toString());
        }
    }


}
