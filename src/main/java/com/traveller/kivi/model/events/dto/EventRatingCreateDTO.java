package com.traveller.kivi.model.events.dto;

import jakarta.validation.constraints.NotNull;

public class EventRatingCreateDTO {

    @NotNull
    public Integer ownerId;
    @NotNull
    public Integer rate;
    @NotNull
    public String comment;
}
