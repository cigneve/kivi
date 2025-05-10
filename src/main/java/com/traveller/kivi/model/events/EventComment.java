package com.traveller.kivi.model.events;

import java.time.LocalDate;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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

    @NotNull
    @ManyToOne
    private User owner;

    @NotNull
    private LocalDate commentDate;

    @NotNull
    @NotEmpty
    private String commentBody;

    @PrePersist
    protected void onCreate() {
        this.commentDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
