package com.traveller.kivi.model.events;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.events.Event.EventType;

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

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @OneToMany
    private Set<EventRating> templateRatings = new HashSet<>();

    @OneToMany
    private List<EventLocation> locations = new ArrayList<>();

    @OneToMany
    List<EventComment> eventComments = new ArrayList<>();

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Set<EventRating> getTemplateRatings() {
        return templateRatings;
    }

    public void setTemplateRatings(Set<EventRating> templateRatings) {
        this.templateRatings = templateRatings;
    }

    public void setLocations(List<EventLocation> locations) {
        this.locations = locations;
    }

    public List<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<EventComment> getComments() {
        return comments;
    }

    public void setComments(List<EventComment> comments) {
        this.comments = comments;
    }

}
