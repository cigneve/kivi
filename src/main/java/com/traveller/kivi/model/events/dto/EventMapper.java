package com.traveller.kivi.model.events.dto;

import java.util.List;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.users.User;

/**
 * Maps between EventCreateDTO and Event entity,
 * and delegates Entity→DTO to EventDetails.
 */
public interface EventMapper {

    /** DTO → Entity */
    static Event toEntity(EventCreateDTO dto, User owner, List<EventLocation> locations) {
        Event e = new Event();
        e.setOwner(owner);
        e.setEventType(dto.getEventType());
        e.setName(dto.getName());
        e.setDetails(dto.getDetails());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setLocations(locations);
        return e;
    }

    /** Entity → EventDetails DTO */
    static EventDetails toDto(Event event) {
        return new EventDetails().toEventDetails(event);
    }
}