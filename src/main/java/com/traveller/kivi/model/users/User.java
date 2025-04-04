package com.traveller.kivi.model.users;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    public enum UserType {
        ADMIN,
        REGULAR_USER,
        GUIDE_USER,
        GUEST
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "follower"), inverseJoinColumns = @JoinColumn(name = "target"))
    Set<User> following;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate registrationDate;

    private User() {
        this.registrationDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

}
