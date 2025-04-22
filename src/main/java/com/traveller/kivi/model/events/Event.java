package com.traveller.kivi.model.events;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "events")
public class Event {
    public enum EventType {
        TOUR,
        MEETUP
    }

    public enum Status {
        SCHEDULED,
        FINISHED,
        CANCELLED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)

    private LocalDate created;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany
    private Set<EventRating> ratings;

    @OneToMany
    private List<EventLocation> locations;

    @NotNull
    @NotEmpty
    private String details;

    @OneToMany
    private List<EventComment> comments;

    public Event() {
        this.created = LocalDate.now();
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
