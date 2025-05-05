package com.traveller.kivi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.EventNotFoundException;
import com.traveller.kivi.model.achievements.CriterionType;
import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private final EventRepository eventRepository;

	@Autowired
	private AchievementService achievementService;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

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
		int totalCreates = eventRepository.countByOwner_Id(event.getOwner().getId());
		achievementService.checkAndAward(
				event.getOwner().getId(),
				CriterionType.EVENT_CREATE.name(),
				totalCreates);
		return EventDetails.toEventDetails(event);
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
}
