package com.traveller.kivi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;
import com.traveller.kivi.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Page<User> getUsersByUserType(UserType userType, Pageable pageable) {
        return userRepository.getUsersByUserType(userType, pageable);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<User> getFollowersOfUser(Integer userId) {
        return userRepository.findByFollowing_Id(userId);
    }

    public Set<User> getFollowedUsers(Integer userId) {
        return userRepository.findById(userId).orElseThrow().getFollowing();
    }

    /**
     * Add an User to other's followed user list.
     * 
     * @param followerId   Id of user that follows
     * @param targetUserId Id of user that is followed
     * @return if the operation was succesful
     */
    public boolean followUser(Integer followerId, Integer targetUserId) {
        User follower = userRepository.findById(followerId).orElseThrow();
        User target = userRepository.findById(targetUserId).orElseThrow();
        Set<User> following = follower.getFollowing();
        if (following.contains(target)) {
            return false;
        }
        following.add(target);
        return true;
    }

    public boolean userExistsById(Integer userId) {
        return userRepository.existsById(userId);
    }
}
