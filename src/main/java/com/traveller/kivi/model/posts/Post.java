package com.traveller.kivi.model.posts;

import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

/**
 * Post is a post by an user that is presented in the social media feed.
 */
@Entity
public class Post {
    @Id
    private Integer id;

    @ManyToMany
    private Set<User> likers;

    @ManyToMany
    private Set<PostTag> tags;

    private String body;

    @ManyToOne
    @NotNull
    private User owner;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
