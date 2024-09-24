package com.TCC.controllers;


import com.TCC.domain.event.Event;
import com.TCC.domain.event.EventDTO;
import com.TCC.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String description
    ){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents(title, location, description));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Event> getEventById (@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent (@RequestBody @Valid EventDTO eventDTO){
        Event event = new Event();
        BeanUtils.copyProperties(eventDTO, event);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(event));
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity<String> deleteEvent (@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.deleteEvent(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Event> updateEvent (@PathVariable(value = "id") String id, @RequestBody @Valid EventDTO eventDTO){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(id, eventDTO));
    }

}
