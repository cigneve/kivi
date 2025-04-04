package com.traveller.kivi.model.events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity

public class EventSkeleton {
    @Id
    @GeneratedValue
    private Integer id;

}
