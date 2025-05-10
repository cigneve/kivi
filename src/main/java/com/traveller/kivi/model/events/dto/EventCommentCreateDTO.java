package com.traveller.kivi.model.events.dto;

import jakarta.validation.constraints.NotNull;

public class EventCommentCreateDTO {

    @NotNull
    public Integer ownerId;
    @NotNull
    public String comment;

}
