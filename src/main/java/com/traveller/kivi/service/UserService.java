package com.traveller.kivi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.UserNotFoundException;
import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.model.users.UserStats;
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

    public InputStreamResource getProfilePicture(Integer userId) {
        User user = getUserById(userId);
        return imageService.getImageContentAsResource(user.getProfilePicture());
    }

    public UserDetail setProfilePicture(Integer userId, InputStreamResource res) {
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
}
