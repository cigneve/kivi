package com.traveller.kivi.model.users;

import java.util.Set;

import com.traveller.kivi.model.users.User.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserCreateUpdate {
    @Email
    public String email;
    public String password;
    public String username;
    public String firstName;
    public String lastName;
    public Set<String> languages;
    public UserType userType;

    public UserCreateUpdate(String email, String password, String username, String firstName, String lastName,
            Set<String> languages) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.languages = languages;
    }

    public UserCreateUpdate() {
        // Default constructor
    }

}
