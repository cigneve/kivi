package com.traveller.kivi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.users.User;
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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/type/{userType}")
    public Page<User> getUsersByType(@Valid @PathVariable User.UserType userType,
            Pageable pageable) {
        return userService.getUsersByUserType(userType, pageable);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<User>> getUserFollowers(@PathVariable Integer userId) {

        // First check if the user exists
        if (!userService.userExistsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        // Find all users who follow the specified user
        Set<User> followers = userService.getFollowersOfUser(userId);
        return ResponseEntity.ok(followers);
    }

    /**
     * Returns the followers of an User
     * 
     * @param userId id of the User
     * @return List of the followers
     */
    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Set<User>> getUserProfilePhoto(@PathVariable Integer userId) {

        if (!userService.userExistsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        Set<User> followers = userService.getProfilePicture(userId);
        return ResponseEntity.ok(followers);
    }
}
