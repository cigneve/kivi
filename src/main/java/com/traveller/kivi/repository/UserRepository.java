package com.traveller.kivi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserType(User.UserType userType);

    Page<User> getUsersByUserType(UserType userType, Pageable pageable);
}
