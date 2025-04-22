package com.traveller.kivi.model.posts;

import com.traveller.kivi.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class PostComment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Post post;

    @NotNull
    private User commenter;

    public User getCommenter() {
        return commenter;
    }

    @NotEmpty
    @NotNull
    private String body;

    public String getBody() {
        return body;
    }
}
