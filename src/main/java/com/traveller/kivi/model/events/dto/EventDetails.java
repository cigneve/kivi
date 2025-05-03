package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.Event.EventType;
import com.traveller.kivi.model.events.Event.Status;
import com.traveller.kivi.model.events.EventLocation;
import com.traveller.kivi.model.events.EventRating;
import com.traveller.kivi.model.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EventDetails {

    @NotNull
    private Integer id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(nullable = false)
    private LocalDate created;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Set<Integer> ratingIds = new HashSet<>();

    @NotNull
    private List<Integer> locationIds = new ArrayList<>();

    @NotNull
    private List<Integer> userIds = new ArrayList<>();

    @NotNull
    private Integer ownerId;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String details;

    @NotNull
    private List<Integer> commentIds = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Integer> getRatingIds() {
        return ratingIds;
    }

    public void setRatingIds(Set<Integer> ratingIds) {
        this.ratingIds = ratingIds;
    }

    public List<Integer> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<Integer> locationIds) {
        this.locationIds = locationIds;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public EventDetails toEventDetails(Event event) {
        EventDetails dto = this;
        dto.setId(event.getId());
        dto.setEventType(event.getEventType());
        dto.setStatus(event.getStatus());
        dto.setCreated(event.getCreated());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setRatingIds(event.getRatings().stream().map(EventRating::getId).collect(Collectors.toSet()));
        dto.setLocationIds(event.getLocations().stream().map(EventLocation::getId).collect(Collectors.toList()));
        dto.setUserIds(event.getAttendants().stream().map(User::getId).collect(Collectors.toList()));
        dto.setOwnerId(event.getOwner().getId());
        dto.setName(event.getName());
        dto.setDetails(event.getDetails());
        return dto;
    }
}
