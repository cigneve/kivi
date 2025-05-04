package com.traveller.kivi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.service.EventService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService= eventService;
    }

    /**
     * Creates new event. 
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {

        Event created = eventService.createEvent(event);
        return ResponseEntity.ok(created);
    }

    /**
     * Returns the list of all events. 
     */
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Returns paginated list of events.
     */
    @GetMapping
    public PagedModel<Event> getPaginatedEvents(Pageable pageable) {
        return new PagedModel<>(eventService.getPaginatedEvents(pageable));
    }

    /**
     * Brings an event according to id.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eventId) {
        if(!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    /**
     * Updates an event.
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer eventId, @Valid @RequestBody Event updated) {
        if(!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        Event result = eventService.updateEvent(eventId, updated);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes an event.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer eventId) {
        if(!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}