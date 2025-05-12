package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.Event.EventType;
import com.traveller.kivi.model.events.Event.Status;
import com.traveller.kivi.model.events.EventRating;
import com.traveller.kivi.model.users.User;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EventDetails {

    @NotNull
    public Integer id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    public EventType eventType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    public Status status;

    @NotNull
    public LocalDate created;

    @NotNull
    public LocalDate startDate;

    @NotNull
    public LocalDate endDate;

    @NotNull
    public Set<Integer> ratingIds = new HashSet<>();

    @NotNull
    public List<EventLocationDTO> locations = new ArrayList<>();

    @NotNull
    public List<Integer> userIds = new ArrayList<>();

    @NotNull
    public Integer ownerId;

    @NotBlank
    @NotNull
    public String name;

    @NotNull
    @NotEmpty
    public String details;

    @NotNull
    @NotEmpty
    public String language;

    @NotNull
    public Integer rating;

    @NotNull
    public List<Integer> commentIds = new ArrayList<>();

    public Integer skeletonId;

    public static EventDetails toEventDetails(Event event) {
        EventDetails dto = new EventDetails();
        dto.id = event.getId();
        dto.eventType = event.getEventType();
        dto.status = event.getStatus();
        dto.created = event.getCreated();
        dto.startDate = event.getStartDate();
        dto.endDate = event.getEndDate();
        dto.ratingIds = event.getRatings().stream().map(EventRating::getId).collect(Collectors.toSet());
        dto.locations = event.getLocations().stream().map(EventLocationDTO::fromEventLocation).toList();
        dto.userIds = event.getAttendants().stream().map(User::getId).collect(Collectors.toList());
        dto.ownerId = event.getOwner().getId();
        dto.name = event.getName();
        dto.details = event.getDetails();
        dto.commentIds = event.getChatComments().stream().map(comment -> comment.getId()).toList();
        dto.skeletonId = event.getSkeleton().getId();
        dto.language = event.getLanguage();
        dto.rating = dto.rating = (int) Math.round(event.getRatings().stream()
                .mapToInt(EventRating::getRate)
                .average()
                .orElse(0));
        ;
        return dto;
    }
}
