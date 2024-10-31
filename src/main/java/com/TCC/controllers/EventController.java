package com.TCC.controllers;

import com.TCC.domain.calendar.CalendarId;
import com.TCC.domain.calendar.TokenRequest;
import com.TCC.domain.event.*;
import com.TCC.infra.security.TokenService;
import com.TCC.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final TokenService tokenService;

    public EventController(EventService eventService, TokenService tokenService) {
        this.eventService = eventService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents(title, startDate, endDate));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @GetMapping("customer/{userId}")
    public ResponseEntity<List<CustomerEventDTO>> getEventsOfCustomer(@PathVariable String userId) {
        return ResponseEntity.ok(eventService.getEventsByUserId(userId));
    }

    @GetMapping("company/{userId}")
    public ResponseEntity<Object> getEventsOfCompany(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(eventService.getEventsOfUserCompany(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> createEvent(@ModelAttribute @Valid EventDTO eventDTO, HttpServletRequest request) {
        try {
            String token = tokenService.extractTokenFromRequest(request);

            return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventDTO, tokenService.getUserIdFromToken(token)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/rate/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> rateEvent(@PathVariable String id, @RequestBody int rate) {
        try {
            eventService.rateEvent(id, rate);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> cancelEvent(@RequestBody @Valid CancelEventRequestDTO requestDTO) {
        try {
            eventService.deleteUserEvent(requestDTO.userId(), requestDTO.eventId());

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> updateEvent(@PathVariable String id, @ModelAttribute @Valid EventDTO eventDTO) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<String> createUserEvent(@RequestBody @Valid UserEventRequestDTO userEventRequestDTO){
        try {
            CancelEventRequestDTO requestDTO = userEventRequestDTO.getRequestDTO();
            eventService.createUserEvent(requestDTO.userId(), requestDTO.eventId());
            eventService.addEventToCalendar(userEventRequestDTO.getTokenRequest(), userEventRequestDTO.getCalendarId(), requestDTO.eventId());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
