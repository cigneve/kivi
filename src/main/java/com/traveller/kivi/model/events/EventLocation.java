package com.traveller.kivi.model.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class EventLocation {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Coordinate location;

    private String title;

    private String description;

    @JsonIgnore
    private boolean featured;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFeatured() {
        return featured;
    }
}
