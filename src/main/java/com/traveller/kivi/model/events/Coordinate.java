package com.traveller.kivi.model.events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public final class Coordinate {
    @Id
    @GeneratedValue
    private Integer id;

    public final double latitude;
    public final double longtitude;

    public Coordinate(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
