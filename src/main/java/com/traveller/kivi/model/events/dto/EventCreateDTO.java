// src/main/java/com/traveller/kivi/model/events/dto/EventCreateDTO.java
package com.traveller.kivi.model.events.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.Event.EventType;
import com.traveller.kivi.model.events.Event.Status;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.users.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a new Event.
 */
public class EventCreateDTO {

    /**
     * Maps necessary objects to create a Event entity.
     * 
     * @param dto
     * @param owner
     * @param locations
     * @return
     */
    public static Event toEntity(EventCreateDTO dto, User owner, List<EventLocation> locations) {
        Event e = new Event();
        e.setOwner(owner);
        e.setEventType(dto.eventType);
        e.setName(dto.name);
        e.setDetails(dto.details);
        e.setStartDate(dto.startDate);
        e.setDuration(dto.duration);
        e.setLocations(locations);
        e.setStatus(Status.SCHEDULED);
        e.setLanguage(dto.language);
        return e;
    }

    @NotNull
    public Integer ownerId;

    @NotNull
    public EventType eventType;

    @NotBlank
    public String name;

    @NotBlank
    public String details;

    @NotNull
    public LocalDateTime startDate;

    @NotNull
    public Integer duration; // Duration in minutes

    @NotNull
    @NotBlank
    public String language;

    /**
     * null if it is newly created
     */
    public Integer skeletonId;

    public List<Integer> locationIds = new ArrayList<>();
}