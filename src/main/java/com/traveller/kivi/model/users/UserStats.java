package com.traveller.kivi.model.users;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for User stats
 */
public class UserStats {
    @NotNull
    public Long followerCount;
    @NotNull
    public Long followedCount;
    @NotNull
    public Long postCount;

    public Long enrolledEventCount;
    public Long createdEventCount;
}
