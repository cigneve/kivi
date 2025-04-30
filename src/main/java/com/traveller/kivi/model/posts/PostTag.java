package com.traveller.kivi.model.posts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;

import java.util.Set;

@Entity
public class PostTag {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    public PostTag() {
    }

    public PostTag(String name) {
        this.name = name;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}