package com.traveller.kivi.model.events;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "events")
public class Event {
    public enum EventType {
        TOUR,
        MEETUP
    }

    public enum Status {
        SCHEDULED,
        FINISHED,
        CANCELLED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)

    private LocalDate created;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<EventRating> ratings = new HashSet<>();

    @OneToMany
    private List<EventLocation> locations = new ArrayList<>();

    @ManyToMany
    private List<User> attendants = new ArrayList<>();

    @ManyToOne
    @NotNull
    private User owner;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String details;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EventComment> chatComments = new ArrayList<>();

    @OneToMany
    private List<Image> gallery = new ArrayList<>();

    @ManyToOne
    @NotNull
    private EventSkeleton skeleton;

    public Event() {
    }

    @PrePersist
    protected void onCreate() {
        this.created = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<EventRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<EventRating> ratings) {
        this.ratings = ratings;
    }

    public void setLocations(List<EventLocation> locations) {
        this.locations = locations;
    }

    public List<User> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<User> attendants) {
        this.attendants = attendants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventLocation> getLocations() {
        return locations;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<EventComment> getChatComments() {
        return chatComments;
    }

    public void setChatComments(List<EventComment> chatComments) {
        this.chatComments = chatComments;
    }

    public List<Image> getGallery() {
        return gallery;
    }

    public void setGallery(List<Image> gallery) {
        this.gallery = gallery;
    }

    public EventSkeleton getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(EventSkeleton skeleton) {
        this.skeleton = skeleton;
    }

}
