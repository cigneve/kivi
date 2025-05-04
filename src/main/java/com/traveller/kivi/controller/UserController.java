package com.traveller.kivi.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Valid
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserDetail> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/type/{userType}")
    public Page<UserDetail> getUsersByType(@Valid @PathVariable User.UserType userType,
            Pageable pageable) {
        return userService.getUsersByUserType(userType, pageable);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserDetail>> getUserFollowers(@PathVariable Integer userId) {

        // First check if the user exists
        if (!userService.userExistsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        // Find all users who follow the specified user
        Set<UserDetail> followers = userService.getFollowersOfUser(userId);
        return ResponseEntity.ok(followers);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Integer> getUserProfilePhoto(@PathVariable Integer userId) {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/nuke")
    public boolean removeAll() {

        userService.removeAll();
        return true;
    }
}
