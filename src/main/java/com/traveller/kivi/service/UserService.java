package com.traveller.kivi.service;

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

    public List<User> getFollowersOfUser(Integer userId) {
        return userRepository.findByFollowing_Id(userId);
    }

    public boolean userExistsById(Integer userId) {
        return userRepository.existsById(userId);
    }
}
