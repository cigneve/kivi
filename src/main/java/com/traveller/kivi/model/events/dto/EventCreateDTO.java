// src/main/java/com/traveller/kivi/model/events/dto/EventCreateDTO.java
package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.traveller.kivi.model.events.Event.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a new Event.
 */
public class EventCreateDTO {

    @NotNull
    private Integer ownerId;

    @NotNull
    private EventType eventType;

    @NotBlank
    private String name;

    @NotBlank
    private String details;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private List<Integer> locationIds = new ArrayList<>();

    // Getters & Setters

    public Integer getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
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

    public List<Integer> getLocationIds() {
        return locationIds;
    }
    public void setLocationIds(List<Integer> locationIds) {
        this.locationIds = locationIds;
    }
}