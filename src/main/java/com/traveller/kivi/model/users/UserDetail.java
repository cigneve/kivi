package com.traveller.kivi.model.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.traveller.kivi.model.users.User.UserType;

public class UserDetail {
    public Set<String> languages = new HashSet<>();
    public LocalDateTime registrationDate;
    public UserType userType;
    public String email;
    public String lastName;
    public String firstName;
    public String username;
    public Integer id;

    public static UserDetail fromUser(User user) {
        UserDetail userDetail = new UserDetail();
        userDetail.email = user.getEmail();
        userDetail.languages = user.getLanguages();
        userDetail.id = user.getId();
        userDetail.firstName = user.getFirstName();
        userDetail.lastName = user.getLastName();
        userDetail.email = user.getEmail();
        userDetail.userType = user.getUserType();
        userDetail.registrationDate = user.getRegistrationDate();
        userDetail.username = user.getUsername();
        return userDetail;
    }
}
