package com.traveller.kivi.model.achievements;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.traveller.kivi.model.users.User;

@Entity
@Table(name ="achievements")

public class Achievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;         // e.g. "FIRST_EVENT"

    @Column(nullable = false)
    private String name;         // e.g. "Created their first event"

    private String description;  // e.g. "You have created your first event in the app!"

    @Column(nullable = false)
    private String criterion;    // e.g. "EVENT_CREATE"

    @Column(nullable = false)
    private Integer threshold;   // e.g. 1, 5, 10…

    @Column(nullable = false)
    private String badgeImageUrl;

    public Achievement(String code, String name, String description, String criterion, Integer threshold, String badgeImageUrl) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.criterion = criterion;
        this.threshold = threshold;
        this.badgeImageUrl= badgeImageUrl;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public void setBadgeImageUrl(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }

    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
        this.id = id; 
    }
    public String getCode() { 
        return code; 
    }
    public void setCode(String code) { 
        this.code = code; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }
    public String getCriterion() { 
        return criterion; 
    }
    public void setCriterion(String criterion) { 
        this.criterion = criterion; 
    }
    public Integer getThreshold() { 
        return threshold; 
    }
    public void setThreshold(Integer threshold) { 
        this.threshold = threshold; 
    }
}
