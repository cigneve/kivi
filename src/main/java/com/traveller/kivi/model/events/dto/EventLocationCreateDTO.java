package com.traveller.kivi.model.events.dto;

import java.util.HashSet;
import java.util.Set;

import com.traveller.kivi.model.events.Coordinate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventLocationCreateDTO {
    @NotNull
    public Coordinate location;

    @NotBlank
    public String title;

    @NotBlank
    public String description;

    public Set<String> keywords = new HashSet<>();

    public boolean featured;
}