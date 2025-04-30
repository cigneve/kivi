package com.traveller.kivi.model.events;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * EventSkeleton is an object representing the pre-entered data for a catalogue
 * tour
 */
@Entity
public class EventSkeleton {
    @Id
    @GeneratedValue
    private Integer id;

    public enum EventType {
        TOUR,
        MEETUP
    }

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @OneToMany
    private Set<EventRating> templateRatings = new HashSet<>();

    @OneToMany
    private List<EventLocation> locations = new ArrayList<>();

    public List<EventLocation> getLocations() {
        return locations;
    }

    @NotNull
    @NotEmpty
    private String details;

    @OneToMany
    private List<EventComment> comments = new ArrayList<>();

    public EventSkeleton() {
    }

    public EventType getEventType() {
        return eventType;
    }

}
