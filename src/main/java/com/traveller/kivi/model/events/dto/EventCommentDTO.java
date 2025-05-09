package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * EventComment DTO.
 * Also used for Event Chat posts
 */
public class EventCommentDTO {
    @NotNull
    public Integer id;

    @NotNull
    public Integer eventId;

    @NotNull
    public Integer ownerId;

    public LocalDate commentDate;

    public String commentBody;
}
