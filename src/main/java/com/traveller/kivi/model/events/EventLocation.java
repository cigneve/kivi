package com.traveller.kivi.model.events;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class EventLocation {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @OneToOne
    private Coordinate location;

    @NotBlank
    private String title;

    private String description;

    private Set<String> keywords;

    @JsonIgnore
    private boolean featured;

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFeatured() {
        return featured;
    }
}
