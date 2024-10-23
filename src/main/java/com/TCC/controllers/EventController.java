package com.TCC.controllers;

import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @PostMapping()
    public ResponseEntity<Event> createEvent(@ModelAttribute @Valid EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.deleteEvent(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String id, @RequestBody @Valid EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(id, eventDTO));
    }

//
//    @GetMapping("/{id}/suggestions")
//    public ResponseEntity<List<String>> getEventSuggestions(@PathVariable String id, @RequestParam String weatherData) {
//
//        Event event = eventService.getEventById(id);
//
//        List<String> suggestions = eventService.suggestEventChanges(event, weatherData);
//        return ResponseEntity.status(HttpStatus.OK).body(suggestions);
//    }
}
