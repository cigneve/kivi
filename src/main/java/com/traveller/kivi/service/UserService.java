package com.traveller.kivi.service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.UserNotFoundException;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.model.users.UserStats;
import com.traveller.kivi.model.users.UserCreateUpdate;
import com.traveller.kivi.repository.EventRepository;
import com.traveller.kivi.repository.PostRepository;
import com.traveller.kivi.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private EmailService emailService;

    public User createUser(@Valid User user) {
        if (user.getProfilePicture() == null) {
            user.setProfilePicture(imageService.getDefaultImage());
        }

        return userRepository.save(user);
    }

    public Page<UserDetail> getUsersByUserType(UserType userType, Pageable pageable) {
        return userRepository.getUsersByUserType(userType, pageable).map(UserDetail::fromUser);
    }

    public List<UserDetail> getAllUsers() {
        return userRepository.findAll().stream().map(UserDetail::fromUser).collect(Collectors.toList());
    }

    public Set<UserDetail> getFollowersOfUser(Integer userId) {
        return userRepository.findByFollowing_Id(userId).stream().map(UserDetail::fromUser)
                .collect(Collectors.toSet());
    }

    public Set<UserDetail> getFollowedUsers(Integer userId) {
        return userRepository.findById(userId).orElseThrow().getFollowing().stream().map(UserDetail::fromUser)
                .collect(Collectors.toSet());
    }

    /**
     * Add an User to other's followed user list.
     * 
     * @param followerId   Id of user that follows
     * @param targetUserId Id of user that is followed
     * @return if the operation was succesful
     */
    public UserDetail followUser(Integer followerId, Integer targetUserId) {
        User follower = getUserById(followerId);
        User target = getUserById(targetUserId);
        Set<User> following = follower.getFollowing();
        if (!following.contains(target)) {
            following.add(target);
            userRepository.save(follower);
        }
        return UserDetail.fromUser(target);
    }

    /**
     * Remove an User from other's followed user list.
     * 
     * @param followerId   Id of user that follows
     * @param targetUserId Id of user that is followed
     * @return the user that follows
     */
    public UserDetail unfollowUser(Integer followerId, Integer targetUserId) {
        User follower = getUserById(followerId);
        User target = getUserById(targetUserId);
        Set<User> following = follower.getFollowing();
        if (following.contains(target)) {
            following.remove(target);
            userRepository.save(follower);
        }
        return UserDetail.fromUser(target);
    }

    public boolean userExistsById(Integer userId) {
        return userRepository.existsById(userId);
    }

    public User getUserById(Integer userId) {
        try {
            return userRepository.findById(userId).get();
        } catch (Exception e) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

    public UserDetail getUserDetail(Integer userId) {
        return UserDetail.fromUser(getUserById(userId));
    }

    public Long getFollowerCount(Integer userId) {
        return userRepository.countByFollower(userId);
    }

    public Long getFollowedCount(Integer userId) {
        return userRepository.countByFollowing(userId);
    }

    public Resource getProfilePicture(Integer userId) {
        User user = getUserById(userId);
        return imageService.getImageContentAsResource(user.getProfilePicture());
    }

    public UserDetail setProfilePicture(Integer userId, Resource res) {
        User user = getUserById(userId);
        imageService.setImageContent(user.getProfilePicture(), res);
        return UserDetail.fromUser(user);
    }

    public void removeAll() {
        userRepository.deleteAll();
    }

    public UserStats getUserStats(Integer userId) {
        getUserById(userId);
        UserStats stats = new UserStats();
        stats.createdEventCount = eventRepository.countByOwner_Id(userId);
        stats.enrolledEventCount = eventRepository.countByAttendants_Id(userId);
        stats.followedCount = userRepository.countByFollowing(userId);
        stats.followerCount = userRepository.countByFollower(userId);
        stats.postCount = postRepository.countByOwner_Id(userId);
        return stats;
    }

    public boolean isPasswordCorrect(Integer userId, String password) {
        User user = getUserById(userId);
        return user.getPassword().equals(password);
    }

    public UserDetail getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return UserDetail.fromUser(user);
    }

    // Helper method to generate a random password
    private String generateRandomPassword() {
        // Generate a random string of 10 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public UserDetail resetPassword(Integer userId) {
        User user = getUserById(userId);
        String newPassword = generateRandomPassword();

        // Update the user's password
        user.setPassword(newPassword);
        userRepository.save(user);

        // Send email with the new password
        String emailSubject = "Kivi - Your Password Has Been Reset";
        String emailBody = String.format(
                "Hello %s,\n\n" +
                        "Your password has been reset. Your new password is: %s\n\n" +
                        "Please login and change your password as soon as possible.\n\n" +
                        "Regards,\n" +
                        "The Kivi Team",
                user.getFirstName(),
                newPassword);

        try {
            emailService.sendSimpleMessage(user.getEmail(), emailSubject, emailBody);
        } catch (Exception e) {
            // Log the error but don't prevent password reset
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }

        return UserDetail.fromUser(user);
    }

    public UserDetail updateUser(Integer userId, UserCreateUpdate userUpdate) {
        User user = getUserById(userId);

        if (userUpdate.firstName != null)
            user.setFirstName(userUpdate.firstName);
        if (userUpdate.lastName != null)
            user.setLastName(userUpdate.lastName);
        if (userUpdate.email != null)
            user.setEmail(userUpdate.email);
        if (userUpdate.username != null)
            user.setUsername(userUpdate.username);
        if (userUpdate.userType != null)
            user.setUserType(userUpdate.userType);
        if (userUpdate.languages != null)
            user.setLanguages(userUpdate.languages);
        if (userUpdate.password != null)
            user.setPassword(userUpdate.password);

        userRepository.save(user);
        return UserDetail.fromUser(user);
    }
}
