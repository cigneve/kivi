package com.traveller.kivi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.events.dto.EventCreateDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.dto.EventMapper;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.EventLocationRepository;
import com.traveller.kivi.repository.EventRepository;
import com.traveller.kivi.repository.UserRepository;
import com.traveller.kivi.service.AchievementService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.traveller.kivi.model.achievements.CriterionType;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventLocationRepository locationRepository;
    private final AchievementService achievementService;

    @PersistenceContext
    private EntityManager em;

    public EventService(EventRepository eventRepository,UserRepository userRepository,
        EventLocationRepository locationRepository,AchievementService achievementService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.achievementService = achievementService;
    }

    /**
     * Retrieves all events.
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Retrieves a paginated list of events.
     */
    public Page<Event> getPaginatedEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
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
        return eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Event not found: " + eventId));
    }

    /**
     * Creates a new event.
     */
    public Event createEvent(Event event) {
        em.persist(event);
        int totalCreates = eventRepository.countByOwner_Id(event.getOwner().getId());
        achievementService.checkAndAward(
            event.getOwner().getId(),
            CriterionType.EVENT_CREATE.name(),
            totalCreates
        );
        return event;
    }

    /** New: DTO-based create flow */
    @Transactional
    public EventDetails createEventFromDTO(EventCreateDTO dto) {
        User owner = userRepository.findById(dto.getOwnerId())
            .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + dto.getOwnerId()));

        List<EventLocation> locs = dto.getLocationIds().stream()
            .map(id -> locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found: " + id)))
            .collect(Collectors.toList());

        Event ev = EventMapper.toEntity(dto, owner, locs);
        Event saved = createEvent(ev);
        return EventMapper.toDto(saved);
    }

    /**
     * Updates an existing event.
     */
    public Event updateEvent(Integer eventId, Event updated) {
        Event existing = getEventById(eventId);
        existing.setName(updated.getName());
        existing.setDetails(updated.getDetails());
        existing.setEventType(updated.getEventType());
        existing.setStatus(updated.getStatus());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        return eventRepository.save(existing);
    }

    /**
     * Deletes an event by its ID.
     */
    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }
}
