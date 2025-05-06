package com.traveller.kivi.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.User.UserType;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserType(User.UserType userType);

    Page<User> getUsersByUserType(UserType userType, Pageable pageable);

    Set<User> findByFollowing_Id(Integer userId);

    /**
     * Follower count
     */
    @Query("SELECT COUNT(u) FROM User u JOIN u.following f WHERE f.id = :userId")
    Long countByFollower(Integer userId);

    /**
     * Followed count
     */
    @Query("SELECT COUNT(u) FROM User u JOIN u.following f WHERE u.id = :userId")
    Long countByFollowing(@Param("userId") Integer userId);
}
