package com.traveller.kivi.model.events.dto;

import java.time.LocalDate;

import com.traveller.kivi.model.events.EventRating;

import jakarta.validation.constraints.NotNull;

public class EventRatingDTO {
    public static EventRatingDTO fromEventRating(EventRating eventRating) {
        var dto = new EventRatingDTO();
        dto.id = eventRating.getId();
        dto.rate = eventRating.getRate();
        dto.comment = eventRating.getComment();
        dto.eventId = eventRating.getEvent().getId();
        return dto;
    }

    @NotNull
    public Integer id;
    @NotNull
    public Integer eventId;
    @NotNull
    public Integer rate;
    @NotNull
    public String comment;
    @NotNull
    public LocalDate date;
}
