package com.traveller.kivi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.EventNotFoundException;
import com.traveller.kivi.model.achievements.CriterionType;
import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.events.dto.EventCommentDTO;
import com.traveller.kivi.model.events.dto.EventCreateDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.dto.EventRatingDTO;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.EventLocationRepository;
import com.traveller.kivi.repository.EventRepository;
import com.traveller.kivi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventLocationRepository locationRepository;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private UserService userService;

    /**
     * Retrieves all events.
     */
    public List<EventDetails> getAllEvents() {
        return eventRepository.findAll().stream().map(EventDetails::toEventDetails).toList();
    }

    /**
     * Retrieves a paginated list of events.
     */
    public Page<EventDetails> getPaginatedEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventDetails::toEventDetails);
    }

    /**
     * Checks if an event exists by its ID.
     */
    public boolean eventExistsById(Integer eventId) {
        return eventRepository.existsById(eventId);
    }

    /**
     * Retrieves an event by its ID.
     */
    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    }

    /**
     * Creates a new event.
     */
    public EventDetails createEvent(Event event) {
        eventRepository.save(event);
        Long totalCreates = eventRepository.countByOwner_Id(event.getOwner().getId());
        achievementService.checkAndAward(
                event.getOwner().getId(),
                CriterionType.EVENT_CREATE.name(),
                totalCreates);
        return EventDetails.toEventDetails(event);
    }

    /** New: DTO-based create flow */
    @Transactional
    public EventDetails createEventFromDTO(EventCreateDTO dto) {
        User owner = userService.getUserById(dto.ownerId);

        List<EventLocation> locs = dto.locationIds.stream()
                .map(id -> locationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Location not found: " + id)))
                .toList();

        Event ev = EventCreateDTO.toEntity(dto, owner, locs);
        return createEvent(ev);
    }

    /**
     * Updates an existing event.
     */
    public EventDetails updateEvent(Integer eventId, Event updated) {
        Event existing = getEventById(eventId);
        existing.setName(updated.getName());
        existing.setDetails(updated.getDetails());
        existing.setEventType(updated.getEventType());
        existing.setStatus(updated.getStatus());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        return EventDetails.toEventDetails(eventRepository.save(existing));
    }

    /**
     * Deletes an event by its ID.
     */
    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }

    /**
     * Returns the list of EventCommentDTO objects
     * 
     * @param eventId event to query
     * @return List of EventCommentDTO's
     */
    public List<EventCommentDTO> getEventComments(Integer eventId) {
        Event event = getEventById(eventId);
        return event.getComments().stream().map(EventCommentDTO::fromEventComment).toList();
    }

    /**
     * Returns the list of EventRatingDTO objects
     * 
     * @param eventId event to query
     * @return
     */
    public List<EventRatingDTO> getEventRatings(Integer eventId) {
        Event event = getEventById(eventId);
        return event.getRatings().stream().map(EventRatingDTO::fromEventRating).toList();
    }
}
