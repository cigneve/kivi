package com.traveller.kivi.model.achievements;

import java.time.LocalDateTime;

public class UserAchievementDTO {

    private String name;
    private String description;
    private String badgeImageUrl;
    private LocalDateTime awardedAt;

    public UserAchievementDTO(String name, String description, String badgeImageUrl, LocalDateTime awardedAt) {
        this.name = name;
        this.description = description;
        this.badgeImageUrl = badgeImageUrl;
        this.awardedAt = awardedAt;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }
}
