package com.traveller.kivi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.traveller.kivi.model.achievements.UserAchievement;
import com.traveller.kivi.model.achievements.UserAchievementDTO;
import com.traveller.kivi.service.AchievementService;


@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    /**
     * Returns the badges the user earned. 
     */
   @GetMapping("/user/{userId}")
    public List<UserAchievementDTO> getUserAchievements(@PathVariable Integer userId) {
        return achievementService.getUserAchievements(userId).stream()
                .map(ua -> new UserAchievementDTO(
                        ua.getAchievement().getName(),
                        ua.getAchievement().getDescription(),
                        ua.getBadgeImageUrl(),
                        ua.getAwardedAt()))
                .toList();
    }
}
