package com.traveller.kivi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.model.users.UserStats;
import com.traveller.kivi.model.users.UserCreateUpdate;
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

    @GetMapping("/{userId}")
    public UserDetail getUserDetail(@PathVariable Integer userId) {
        return userService.getUserDetail(userId);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserDetail>> getUserFollowers(@PathVariable Integer userId) {

        // Find all users who follow the specified user
        Set<UserDetail> followers = userService.getFollowersOfUser(userId);
        return ResponseEntity.ok(followers);
    }

    /**
     * Returns the followed users
     * 
     * @param userId id of the User
     * @return List of the followed users
     */
    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<UserDetail>> getUserFollowing(@PathVariable Integer userId) {

        Set<UserDetail> followingList = userService.getFollowedUsers(userId);
        return ResponseEntity.ok(followingList);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/avatar")
    public Resource getUserProfilePhoto(@PathVariable Integer userId) {

        return userService.getProfilePicture(userId);
    }

    /**
     * Set the profile photo of an user
     * 
     * @param userId
     * @param res
     * @return
     */
    @PostMapping(path = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDetail setUserProfilePhoto(@PathVariable Integer userId, @RequestParam("image") MultipartFile image) {
        Resource res;
        try {
            res = new InputStreamResource(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error getting avatar of User with id: " + userId);
        }
        return userService.setProfilePicture(userId, res);
    }

    @PostMapping("/{userId}/follow")
    public UserDetail followUser(@PathVariable Integer userId, @RequestParam Integer targetUserId) {
        return userService.followUser(userId, targetUserId);
    }

    @PostMapping("/{userId}/unfollow")
    public UserDetail unfollowUser(@PathVariable Integer userId, @RequestParam Integer targetUserId) {
        return userService.unfollowUser(userId, targetUserId);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/stats")
    public UserStats getUserStats(@PathVariable Integer userId) {
        return userService.getUserStats(userId);
    }

    @GetMapping("/{userId}/checkPassword")
    public Boolean checkPassword(@PathVariable Integer userId, @RequestParam String password) {
        return userService.isPasswordCorrect(userId, password);
    }

    @PostMapping("/{userId}/resetPassword")
    public UserDetail resetPassword(@PathVariable Integer userId) {
        return userService.resetPassword(userId);
    }

    @PostMapping("/{userId}/update")
    public UserDetail updateUser(@PathVariable Integer userId, @Valid @RequestBody UserCreateUpdate userUpdate) {
        return userService.updateUser(userId, userUpdate);
    }

    @GetMapping("/byEmail/{email}")
    public UserDetail getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/nuke")
    public boolean removeAll() {

        userService.removeAll();
        return true;
    }
}
