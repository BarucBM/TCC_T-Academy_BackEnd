package com.TCC.controllers;

import com.TCC.domain.event.CancelEventRequestDTO;
import com.TCC.domain.event.CustomerEventDTO;
import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){

        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents(title, startDate, endDate));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<CustomerEventDTO>> getEventsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(eventService.getEventsByUserId(userId));
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@ModelAttribute @Valid EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventDTO));
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<String> rateEvent(@PathVariable String id, @RequestBody int rate) {
        try {
            eventService.rateEvent(id, rate);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelEvent(@RequestBody @Valid CancelEventRequestDTO requestDTO) {
        try {
            eventService.deleteUserEvent(requestDTO.userId(), requestDTO.eventId());

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable String id, @ModelAttribute @Valid EventDTO eventDTO) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
