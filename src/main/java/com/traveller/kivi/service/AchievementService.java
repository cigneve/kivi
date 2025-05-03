package com.traveller.kivi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import com.traveller.kivi.model.achievements.Achievement;
import com.traveller.kivi.model.achievements.UserAchievement;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.AchievementRepository;
import com.traveller.kivi.repository.UserAchievementRepository;

import jakarta.transaction.Transactional;

@Service
public class AchievementService {
    
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private UserService userService;

    /**
     * It checks all Achievements for the relevant criterion according to the user's currentCount 
     * value and assigns badges to those who pass the threshold.
     */
    @Transactional
    public void checkAndAward(Integer userId, String criterion, int currentCount) {
        User user = userService.getUserById(userId);
        List<Achievement> list = achievementRepository.findByCriterion(criterion);
        for (Achievement a : list) {
            if (currentCount >= a.getThreshold()
                && !userAchievementRepository.existsByUserAndAchievement(user, a)) {
                UserAchievement userAchievement = new UserAchievement(user, a, LocalDateTime.now());
                userAchievementRepository.save(userAchievement);
            }
        }
    }

    /**
     * Returns all badges earned by a user.
     */
    public List<UserAchievement> getUserAchievements(Integer userId) {
        User user = userService.getUserById(userId);
        return userAchievementRepository.findByUser(user);
    }

}
