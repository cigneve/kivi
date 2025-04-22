package com.traveller.kivi.model.events;

import java.time.LocalDate;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class EventComment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Event event;

    @NotNull
    private User owner;

    @NotNull
    private LocalDate commentDate;

    @NotNull
    @NotEmpty
    private String commentBody;
}
