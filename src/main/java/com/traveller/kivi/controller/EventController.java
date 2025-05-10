package com.traveller.kivi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.events.dto.EventCommentDTO;
import com.traveller.kivi.model.events.dto.EventCreateDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.dto.EventRatingDTO;
import com.traveller.kivi.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping()
    public EventDetails createEvent(@Valid @RequestBody EventCreateDTO dto) {
        return eventService.createEvent(dto);
    }

    /**
     * Returns paginated list of events.
     */
    @GetMapping
    public PagedModel<EventDetails> getPaginatedEvents(Pageable pageable) {
        return new PagedModel<>(eventService.getPaginatedEvents(pageable));
    }

    /**
     * Returns the list of all events.
     */
    @GetMapping("/all")
    public List<EventDetails> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Returns the list of all events owned by an user.
     * 
     * @param userId
     * @return
     */
    @GetMapping("/owned/{userId}")
    public List<EventDetails> getOwnedEvents(Integer userId) {
        return eventService.getOwnedEvents(userId);
    }

    /**
     * Brings an event according to id.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetails> getEvent(@PathVariable Integer eventId) {
        if (!eventService.eventExistsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EventDetails.toEventDetails(eventService.getEventById(eventId)));
    }

    /**
     * Updates an event.
     */
    @PutMapping("/{eventId}")
    public EventDetails updateEvent(@PathVariable Integer eventId, @Valid @RequestBody EventCreateDTO updated) {
        // TODO
        throw new UnsupportedOperationException();
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

    @GetMapping("/{eventId}/ratings")
    public List<EventRatingDTO> getEventRatings(@PathVariable Integer eventId) {
        return eventService.getEventRatings(eventId);
    }

    @GetMapping("/{eventId}/chat")
    public List<EventRatingDTO> getEventChatComments(@PathVariable Integer eventId) {
        return eventService.getEventChatComments(eventId);
    }

    /**
     * Returns the list of all events attended by an user.
     * 
     * @param userId
     * @return
     */
    @GetMapping("/attended/{userId}")
    public List<EventDetails> getAttendedEvents(Integer userId) {
        return eventService.getAttendedEvents(userId);
    }

    @PutMapping("/{eventId}/addUser/{userId}")
    public String addUserToEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        return eventService.addUserToEvent(eventId, userId);
    }

    @PutMapping("/{eventId}/cancel")
    public String cancelEvent(@PathVariable Long eventId) {
        return eventService.cancelEvent(eventId);
    }

    @PutMapping("/{eventId}/register/{userId}")
    public String registerToEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        return eventService.registerToEvent(eventId, userId);
    }
}