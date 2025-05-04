package com.traveller.kivi.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        return eventRepository.save(event);
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
