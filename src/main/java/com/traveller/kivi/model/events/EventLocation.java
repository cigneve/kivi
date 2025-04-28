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
<<<<<<< HEAD
=======
    @Id
    @GeneratedValue
    private Integer id;

>>>>>>> b49851414f54adc14515e65b2f281bc3e63ecb67
    @NotNull
    @OneToOne
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
