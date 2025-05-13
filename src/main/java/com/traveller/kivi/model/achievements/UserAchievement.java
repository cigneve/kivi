package com.traveller.kivi.model.achievements;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.traveller.kivi.model.users.User;

@Entity
@Table(name = "user_achievements")

public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @Column(nullable = false)
    private LocalDateTime awardedAt;
    

    public UserAchievement(User user, Achievement achievement, LocalDateTime awardedAt) {
        this.user = user;
        this.achievement = achievement;
        this.awardedAt = awardedAt;
    }


    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
        this.id = id; 
    }
    public User getUser() { 
        return user; 
    }
    public void setUser(User user) { 
        this.user = user; 
    }
    public Achievement getAchievement() { 
        return achievement; 
    }
    public void setAchievement(Achievement achievement) { 
        this.achievement = achievement; 
    }
    public LocalDateTime getAwardedAt() { 
        return awardedAt; 
    }
    public void setAwardedAt(LocalDateTime awardedAt) { 
        this.awardedAt = awardedAt; 
    }

    public String getBadgeImageUrl() {
        return achievement.getBadgeImageUrl();
    }
    
}