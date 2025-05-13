package com.traveller.kivi.model.achievements;

public enum CriterionType {
    EVENT_CREATE("images/badges/event_create.png"),
    EVENT_JOIN("images/badges/event_join.png"),
    POST_CREATE("images/badges/post_create.png"),
    IMAGE_UPLOAD("images/badges/image_upload.png"),
    COMMENT_WRITE("images/badges/comment_write.png"),
    LIKE_RECEIVE("images/badges/like_receive.png");

    private final String badgeImageUrl;

    CriterionType(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }
}

