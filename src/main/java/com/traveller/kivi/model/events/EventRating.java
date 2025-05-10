package com.traveller.kivi.model.events;

import org.hibernate.validator.constraints.Range;

import com.traveller.kivi.model.users.User;

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
    private Event event;

    @NotNull
    @Range(min = 0, max = 10)
    private Integer rate;

    @NotNull
    private String comment;

    @NotNull
    @ManyToOne
    private User owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
