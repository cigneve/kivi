package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.traveller.kivi.model.achievements.UserAchievement;
import com.traveller.kivi.model.achievements.Achievement;
import com.traveller.kivi.model.users.User;
import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {
    boolean existsByUserAndAchievement(User user, Achievement achievement);
    List<UserAchievement> findByUser(User user);
}
