package com.traveller.kivi.model.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.traveller.kivi.model.Image;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "[^\\s]+")
    private String username;

    private String password;

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "follower"), inverseJoinColumns = @JoinColumn(name = "target"))
    Set<User> following;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    private LocalDateTime registrationDate;

    @JsonIgnore
    @ManyToOne
    private Image profilePicture;

    @JsonIgnore
    private Set<String> languages = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public User() {
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

}
