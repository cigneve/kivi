package com.traveller.kivi.model.events;

import java.time.LocalDate;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * EventComments are shown in the Chat page.
 * TODO: confusing name
 */
@Entity
public class EventComment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Event event;

    @NotNull
    @ManyToOne
    private User owner;

    @NotNull
    private LocalDate commentDate;

    @NotNull
    @NotEmpty
    private String commentBody;

    /** Needed by the mapper: */
   public Integer getId() {
        return id;
   }
}
