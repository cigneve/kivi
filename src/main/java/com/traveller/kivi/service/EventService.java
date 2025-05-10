package com.traveller.kivi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.EventNotFoundException;
import com.traveller.kivi.model.achievements.CriterionType;
import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.EventComment;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.events.EventRating;
import com.traveller.kivi.model.events.EventSkeleton;
import com.traveller.kivi.model.events.dto.EventCommentCreateDTO;
import com.traveller.kivi.model.events.dto.EventCommentDTO;
import com.traveller.kivi.model.events.dto.EventCreateDTO;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.dto.EventLocationCreateDTO;
import com.traveller.kivi.model.events.dto.EventLocationDTO;
import com.traveller.kivi.model.events.dto.EventRatingCreateDTO;
import com.traveller.kivi.model.events.dto.EventRatingDTO;
import com.traveller.kivi.model.events.dto.EventSkeletonDTO;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.EventLocationRepository;
import com.traveller.kivi.repository.EventRepository;
import com.traveller.kivi.repository.EventSkeletonRepository;
import com.traveller.kivi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventLocationRepository locationRepository;
    @Autowired
    private EventSkeletonRepository eventSkeletonRepository;

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
        return event.getSkeleton().getComments().stream().map(EventCommentDTO::fromEventComment).toList();
    }

    /**
     * Returns the list of EventRatingDTO objects
     * 
     * @param eventId event to query
     * @return
     */
    public List<EventRatingDTO> getEventRatings(Integer eventId) {
        Event event = getEventById(eventId);
        return event.getRatings().stream().map(EventRatingDTO::fromEventRating).sorted((o1, o2) -> {
            return o1.date.compareTo(o2.date);
        }).toList();
    }

    @Transactional
    public EventDetails registerToEvent(Integer eventId, Integer userId) {
        Event event = getEventById(eventId);
        User user = userService.getUserById(userId);
        if (!event.getAttendants().contains(user)) {
            event.getAttendants().add(user);
            eventRepository.save(event);
        }
        return EventDetails.toEventDetails(event);
    }

    public EventDetails createEvent(EventCreateDTO dto) {
        Event event;
        if (dto.skeletonId != null) {
            event = createEventFromSkeleton(dto);
        } else {
            event = createIndependentEvent(dto);
        }
        return EventDetails.toEventDetails(event);
    }

    private Event createEventFromSkeleton(EventCreateDTO dto) {
        EventSkeleton skeleton = eventSkeletonRepository.findById(dto.skeletonId)
                .orElseThrow(() -> new IllegalArgumentException("EventSkeleton not found with ID: " + dto.skeletonId));
        Event event = EventCreateDTO.toEntity(dto, userService.getUserById(dto.ownerId), skeleton.getLocations());
        event.setSkeleton(skeleton);
        return eventRepository.save(event);
    }

    private Event createIndependentEvent(EventCreateDTO dto) {
        User user = userService.getUserById(dto.ownerId);
        Event event = EventCreateDTO.toEntity(dto, user,
                dto.locationIds.stream().map(id -> getEventLocationById(id)).toList());
        EventSkeleton skeleton = new EventSkeleton();
        skeleton.setOwner(user);
        skeleton.setDetails(dto.details);

        skeleton.setEventType(event.getEventType());
        skeleton.setLocations(event.getLocations());
        eventSkeletonRepository.save(skeleton);
        event.setSkeleton(skeleton);
        return eventRepository.save(event);
    }

    /**
     * List of owned events.
     * 
     * @param userId owner id
     * @return
     */
    public List<EventDetails> getOwnedEvents(Integer userId) {
        return eventRepository.findByOwnerId(userId).stream().map(EventDetails::toEventDetails).toList();
    }

    public List<EventCommentDTO> getEventChatComments(Integer eventId) {
        Event event = getEventById(eventId);
        return event.getChatComments().stream().map(EventCommentDTO::fromEventComment).sorted((o1, o2) -> {
            return o1.commentDate.compareTo(o2.commentDate);
        }).toList();
    }

    /**
     * List of attended events.
     * 
     * @param attendantId
     * @return
     */
    public List<EventDetails> getAttendedEvents(Integer userId) {
        return eventRepository.findByAttendantsId(userId).stream().map(EventDetails::toEventDetails).toList();
    }

    public String cancelEvent(Integer eventId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelEvent'");
    }

    @Transactional
    public EventCommentDTO postChatComment(Integer eventId, EventCommentCreateDTO commentDTO) {
        Event event = getEventById(eventId);
        EventComment comment = toEventComment(eventId, commentDTO);
        event.getChatComments().add(comment);
        eventRepository.save(event);
        return EventCommentDTO.fromEventComment(comment);
    }

    @Transactional
    public EventRatingDTO postRating(Integer eventId, EventRatingCreateDTO ratingDTO) {
        Event event = getEventById(eventId);
        EventRating rating = toEventRating(eventId, ratingDTO);
        event.getRatings().add(rating);
        eventRepository.save(event);
        return EventRatingDTO.fromEventRating(rating);
    }

    @Transactional
    public EventCommentDTO postEventComment(Integer eventId, EventCommentCreateDTO commentDTO) {
        Event event = getEventById(eventId);
        EventComment comment = toEventComment(eventId, commentDTO);
        EventSkeleton skeleton = event.getSkeleton();
        skeleton.getComments().add(comment);
        eventSkeletonRepository.save(skeleton);
        return EventCommentDTO.fromEventComment(comment);
    }

    private EventRating toEventRating(Integer eventId, EventRatingCreateDTO eventRatingCreateDTO) {
        var dto = new EventRating();
        dto.setOwner(userService.getUserById(eventRatingCreateDTO.ownerId));
        dto.setComment(eventRatingCreateDTO.comment);
        dto.setRate(eventRatingCreateDTO.rate);
        dto.setEvent(getEventById(eventId));
        dto.setDate(LocalDateTime.now());
        return dto;
    }

    private EventComment toEventComment(Integer eventId, EventCommentCreateDTO eventCommentCreateDTO) {
        var dto = new EventComment();
        dto.setCommentBody(eventCommentCreateDTO.comment);
        dto.setOwner(userService.getUserById(eventCommentCreateDTO.ownerId));
        return dto;
    }

    public EventSkeletonDTO getEventSkeleton(Integer eventId) {
        return EventSkeletonDTO.fromEventSkeleton(getEventById(eventId).getSkeleton());
    }

    public EventLocationDTO createEventLocation(EventLocationCreateDTO dto) {
        EventLocation location = new EventLocation();
        location.setLocation(dto.location);
        location.setTitle(dto.title);
        location.setDescription(dto.description);
        location.setKeywords(dto.keywords);
        location.setFeatured(dto.featured);

        EventLocation savedLocation = locationRepository.save(location);
        return EventLocationDTO.fromEventLocation(savedLocation);
    }

    public EventLocationDTO getEventLocationDTOById(Integer locationId) {
        return EventLocationDTO.fromEventLocation(getEventLocationById(locationId));
    }

    public EventLocation getEventLocationById(Integer locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + locationId));
    }

    public List<EventLocationDTO> getAllEventLocations() {
        return locationRepository.findAll().stream()
                .map(EventLocationDTO::fromEventLocation)
                .toList();
    }

    @Transactional
    public EventLocationDTO updateEventLocation(Integer locationId, EventLocationCreateDTO dto) {
        EventLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + locationId));

        location.setLocation(dto.location);
        location.setTitle(dto.title);
        location.setDescription(dto.description);
        location.setKeywords(dto.keywords);
        location.setFeatured(dto.featured);

        return EventLocationDTO.fromEventLocation(locationRepository.save(location));
    }

    public void deleteEventLocation(Integer locationId) {
        locationRepository.deleteById(locationId);
    }

    public EventSkeletonDTO getEventSkeletonById(Integer skeletonId) {
        return EventSkeletonDTO.fromEventSkeleton(eventSkeletonRepository.findById(skeletonId).orElseThrow(() -> {
            throw new IllegalArgumentException("No such skeleton");
        }));
    }

}
