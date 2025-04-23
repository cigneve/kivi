package com.traveller.kivi.model.posts;

import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

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

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
