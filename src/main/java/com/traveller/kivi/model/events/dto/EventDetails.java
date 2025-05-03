package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.events.Event.EventType;
import com.traveller.kivi.model.events.Event.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EventDetails {

    @NotNull
    private Integer id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(nullable = false)
    private LocalDate created;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Set<Integer> ratingIds = new HashSet<>();

    @NotNull
    private List<Integer> locationIds = new ArrayList<>();

    @NotNull
    private List<Integer> userIds = new ArrayList<>();

    @NotNull
    private Integer ownerId;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String details;

    @NotNull
    private List<Integer> commentIds = new ArrayList<>();

}
