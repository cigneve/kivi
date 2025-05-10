package com.traveller.kivi.model.events.dto;

import java.util.HashSet;
import java.util.Set;

import com.traveller.kivi.model.events.Coordinate;
import com.traveller.kivi.model.events.EventLocation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventLocationDTO {
    public static EventLocationDTO fromEventLocation(EventLocation eventLocation) {
        var dto = new EventLocationDTO();
        dto.id = eventLocation.getId();
        dto.location = eventLocation.getLocation();
        dto.title = eventLocation.getTitle();
        dto.description = eventLocation.getDescription();
        dto.keywords = eventLocation.getKeywords();
        dto.featured = eventLocation.isFeatured();
        return dto;
    }

    @NotNull
    public Integer id;

    @NotNull
    public Coordinate location;

    @NotBlank
    public String title;

    @NotBlank
    public String description;

    public Set<String> keywords = new HashSet<>();

    public boolean featured;

}
