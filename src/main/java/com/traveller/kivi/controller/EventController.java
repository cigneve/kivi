package com.traveller.kivi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.traveller.kivi.model.events.dto.EventCommentCreateDTO;
import com.traveller.kivi.model.events.dto.EventCommentDTO;
import com.traveller.kivi.model.events.dto.EventCreateDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.dto.EventLocationCreateDTO;
import com.traveller.kivi.model.events.dto.EventLocationDTO;
import com.traveller.kivi.model.events.dto.EventRatingCreateDTO;
import com.traveller.kivi.model.events.dto.EventRatingDTO;
import com.traveller.kivi.model.events.dto.EventSkeletonDTO;
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

    @GetMapping("/by-location")
    public List<EventDetails> getEventsByLocation(@RequestParam String locationName) {
        return eventService.getEventsByLocationName(locationName);
    }

    @GetMapping("/by-owner")
    public List<EventDetails> getEventsByOwner(@RequestParam String ownerName) {
        return eventService.getEventsByOwnerName(ownerName);
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
    public List<EventDetails> getOwnedEvents(@PathVariable Integer userId) {
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
    public EventDetails updateEvent(@PathVariable Integer eventId, @RequestBody EventCreateDTO updated) {
        return eventService.updateEvent(eventId, updated);
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
    public List<EventCommentDTO> getEventChatComments(@PathVariable Integer eventId) {
        return eventService.getEventChatComments(eventId);
    }

    /**
     * Returns the list of all events attended by an user.
     * 
     * @param userId
     * @return
     */
    @GetMapping("/attended/{userId}")
    public List<EventDetails> getAttendedEvents(@PathVariable Integer userId) {
        return eventService.getAttendedEvents(userId);
    }

    @PutMapping("/{eventId}/cancel")
    public String cancelEvent(@PathVariable Integer eventId) {
        return eventService.cancelEvent(eventId);
    }

    @PutMapping("/{eventId}/register/{userId}")
    public EventDetails registerToEvent(@PathVariable Integer eventId, @PathVariable Integer userId) {
        return eventService.registerToEvent(eventId, userId);
    }

    @PutMapping("/{eventId}/unregister/{userId}")
    public EventDetails unregisterToEvent(@PathVariable Integer eventId, @PathVariable Integer userId) {
        return eventService.unregisterToEvent(eventId, userId);
    }

    @PostMapping("/{eventId}/comments")
    public EventCommentDTO postComment(@PathVariable Integer eventId, @RequestBody EventCommentCreateDTO commentDTO) {
        return eventService.postEventComment(eventId, commentDTO);
    }

    @PostMapping("/{eventId}/ratings")
    public EventRatingDTO postRating(@PathVariable Integer eventId, @RequestBody EventRatingCreateDTO commentDTO) {
        return eventService.postRating(eventId, commentDTO);
    }

    @PostMapping("/{eventId}/chat")
    public EventCommentDTO postChatComment(@PathVariable Integer eventId,
            @RequestBody EventCommentCreateDTO commentDTO) {
        return eventService.postChatComment(eventId, commentDTO);
    }

    @GetMapping("/{eventId}/skeleton")
    public EventSkeletonDTO getEventSkeleton(@PathVariable Integer eventId) {
        return eventService.getEventSkeleton(eventId);
    }

    @GetMapping("/skeleton/{skeletonId}")

    public @Valid EventSkeletonDTO getEventSkeletonById(@PathVariable Integer skeletonId) {
        return eventService.getEventSkeletonById(skeletonId);
    }

    @PostMapping("/locations")
    public EventLocationDTO createEventLocation(@Valid @RequestBody EventLocationCreateDTO dto) {
        return eventService.createEventLocation(dto);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<EventLocationDTO> getEventLocation(@PathVariable Integer locationId) {
        try {
            EventLocationDTO location = eventService.getEventLocationDTOById(locationId);
            return ResponseEntity.ok(location);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/locations/{locationId}/photo")
    public Resource getEventLocationImage(@PathVariable Integer locationId) {
        try {
            return eventService.getEventLocationPhoto(locationId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @PostMapping(path = "/locations/{locationId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EventLocationDTO setEventLocationPhoto(@PathVariable Integer locationId,
            @RequestParam("image") MultipartFile image) {
        Resource res;
        try {
            res = new InputStreamResource(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error getting image of Event with id: " + locationId);
        }

        return eventService.setEventLocationPhoto(locationId, res);
    }

    @GetMapping("/locations/featured")
    public List<EventLocationDTO> getFeaturedEventLocations() {
        return eventService.getFeaturedEventLocations();
    }

    @GetMapping("/locations")
    public List<EventLocationDTO> getAllEventLocations() {
        return eventService.getAllEventLocations();
    }

    @PutMapping("/locations/{locationId}")
    public ResponseEntity<EventLocationDTO> updateEventLocation(
            @PathVariable Integer locationId,
            @Valid @RequestBody EventLocationCreateDTO dto) {
        try {
            EventLocationDTO updated = eventService.updateEventLocation(locationId, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<Void> deleteEventLocation(@PathVariable Integer locationId) {
        try {
            eventService.deleteEventLocation(locationId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{eventId}/photo")
    public Resource getEventPhoto(@PathVariable Integer eventId) {
        return eventService.getEventPhoto(eventId);
    }

    @PostMapping("/{eventId}/photo")
    public EventDetails setEventPhoto(@PathVariable Integer eventId, @RequestParam("image") MultipartFile image) {
        Resource res;
        try {
            res = new InputStreamResource(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error getting image of Event with id: " + eventId);
        }

        return eventService.setEventPhoto(eventId, res);
    }

    @GetMapping("/{userId}/upcomingTours")
    public List<EventDetails> getUpcomingTours(@PathVariable Integer userId) {
        return eventService.getUpcomingEventsByAttendant(userId);
    }

    @GetMapping("/{eventId}/hasRated")
    public Boolean hasUserRated(@PathVariable Integer eventId, @RequestParam Integer userId) {
        return eventService.hasUserRated(userId, eventId);
    }

    @GetMapping("/skeletonsOf/{userId}")
    public List<EventSkeletonDTO> getSkeletonsOfUser(@PathVariable Integer userId) {
        return eventService.getSkeletonsOfUser(userId);
    }

}