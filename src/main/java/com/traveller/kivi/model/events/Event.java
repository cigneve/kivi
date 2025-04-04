package com.traveller.kivi.model.events;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {
    public enum EventType {
        TOUR,
        MEETUP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated
    private EventType eventType;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @OneToOne
    private EventLocation eventLocation;

    /**
     * @param id
     * @param eventType
     * @param startDate
     * @param endDate
     */
    public Event(Integer id, EventType eventType, LocalDate startDate, LocalDate endDate) {
        this.created = LocalDate.now();
        this.id = id;
        this.eventType = eventType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private Event() {
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
