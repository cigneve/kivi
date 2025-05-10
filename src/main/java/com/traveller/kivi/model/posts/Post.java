package com.traveller.kivi.model.posts;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.users.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Post is a post by an user that is presented in the social media feed.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany
    private Set<User> likers = new HashSet<>();

    @ManyToMany
    private Set<PostTag> tags = new HashSet<>();

    @OneToOne
    @Nullable
    private Image image;

    private String body;

    private LocalDate created = LocalDate.now();

    @ManyToOne
    @NotNull
    private User owner;

    public Post() {
    }

    public Post(@NotNull User owner) {
        this.owner = owner;
    }

    @PrePersist
    protected void onCreate() {
        this.created = LocalDate.now();
    }

    public Post(@NotNull User owner, @NotBlank String body, Image image) {
        this.owner = owner;
        this.body = body;
        this.image = image;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Set<User> getLikers() {
        return likers;
    }

    public Set<PostTag> getTags() {
        return tags;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Image getImage() {
        return image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLikers(Set<User> likers) {
        this.likers = likers;
    }

    public void setTags(Set<PostTag> tags) {
        this.tags = tags;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
