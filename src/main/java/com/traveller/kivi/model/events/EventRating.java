package com.traveller.kivi.model.events;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class EventRating {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private EventRating event;

    @NotNull
    @Range(min = 0, max = 10)
    private Integer rate;

    @NotNull
    private String comment;

    public String getComment() {
        return comment;
    }
}
