package com.traveller.kivi.model.posts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany
    private List<Image> images;

    private String body;

    @ManyToOne
    @NotNull
    private User owner;

    public Post() {

    }

    public Post(@NotNull User owner) {
        this.owner = owner;
    }

    public Post(@NotNull User owner, @NotBlank String body, List<Image> images) {
        this.owner = owner;
        this.body = body;
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
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
}
