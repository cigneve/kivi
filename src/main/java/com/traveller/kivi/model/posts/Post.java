package com.traveller.kivi.model.posts;

import java.util.List;
import java.util.Set;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Post {
    @Id
    private Integer id;

    @ManyToMany
    private Set<User> likers;

    @ManyToMany
    private Set<PostTag> tags;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;
}
