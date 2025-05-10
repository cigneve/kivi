package com.traveller.kivi.model.events.dto;

import java.util.ArrayList;
import java.util.List;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.EventSkeleton;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EventSkeletonDTO {

    public static EventSkeletonDTO fromEventSkeleton(EventSkeleton skeleton) {
        var dto = new EventSkeletonDTO();
        dto.details = skeleton.getDetails();
        dto.id = skeleton.getId();
        dto.locations = skeleton.getLocations().stream().map(EventLocationDTO::fromEventLocation).toList();
        dto.ownerId = skeleton.getOwner().getId();
        dto.type = skeleton.getEventType();
        return dto;
    }

    @NotNull
    public Integer id;

    @NotNull
    public Integer ownerId;

    @NotNull
    public List<EventLocationDTO> locations = new ArrayList<>();

    @NotNull
    public Event.EventType type;

    @NotNull
    @NotEmpty
    public String details;

}
