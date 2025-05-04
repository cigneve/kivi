package com.traveller.kivi.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.UserNotFoundException;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(@Valid User user) {
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

    public User getUserById(Integer userId) {
        try {
            return userRepository.findById(userId).get();
        } catch (Exception e) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

    public Set<User> getProfilePicture(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProfilePicture'");
    }

    public void removeAll() {
        userRepository.deleteAll();
    }
}
