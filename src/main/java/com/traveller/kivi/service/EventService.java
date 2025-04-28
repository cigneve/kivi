package com.traveller.kivi.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.traveller.kivi.model.events.Event;

@Service
@Transactional
public class EventService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Creates new event.
     */
    public Event createEvent(Event event) {
        em.persist(event);
        return event;
    }

    /**
     * Brings Event according to Id. If not found throws IllegalArgumentException.
     */
    public Event getEventById(Integer eventId) {
        Event event = em.find(Event.class, eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found: " + eventId);
        }
        return event;
    }

    /**
     * Lists all events. 
     */
    public List<Event> getAllEvents() {
        return em
            .createQuery("SELECT e FROM Event e", Event.class)
            .getResultList();
    }

    /**
     * Updates the Event with the specified Id.
     */
    public Event updateEvent(Integer eventId, Event updated) {
        Event existing = getEventById(eventId);
        existing.setEventType(updated.getEventType());
        existing.setStatus(updated.getStatus());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        em.merge(existing);
        return existing;
    }

    /**
     * Deletes Id according to the Id.
     */
    public void deleteEvent(Integer eventId) {
        Event e = getEventById(eventId);
        em.remove(e);
    }
}
