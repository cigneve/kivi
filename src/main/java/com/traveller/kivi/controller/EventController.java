package com.traveller.kivi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.dto.EventCommentDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.service.EventService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Creates new event.
     */
    @PostMapping
    public ResponseEntity<EventDetails> createEvent(@Valid @RequestBody Event event) {

        EventDetails created = eventService.createEvent(event);
        return ResponseEntity.ok(created);
    }

    /**
     * Returns the list of all events.
     */
    @GetMapping("/all")
    public List<EventDetails> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Returns paginated list of events.
     */
    @GetMapping
    public PagedModel<EventDetails> getPaginatedEvents(Pageable pageable) {
        return new PagedModel<>(eventService.getPaginatedEvents(pageable));
    }

    /**
     * Brings an event according to id.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eventId) {
        if (!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    /**
     * Updates an event.
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDetails> updateEvent(@PathVariable Integer eventId, @Valid @RequestBody Event updated) {
        if (!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        EventDetails result = eventService.updateEvent(eventId, updated);
        return ResponseEntity.ok(result);
    }

    /**
     * Deletes an event.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer eventId) {
        if (!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}/comments")
    public List<EventCommentDTO> getEventComment(@PathVariable Integer eventId) {
        return eventService.getEventComments(eventId);
    }

}