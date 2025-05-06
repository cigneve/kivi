package com.traveller.kivi.model.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.traveller.kivi.model.Image;

/**
 * DTO
 */
public class PostDetail {

    private Integer postId; // Unique identifier for the post
    private Integer userId; // ID of the user who created the post
    private String body; // Content of the post
    private List<String> imageIds = new ArrayList<>(); // List of image URLs associated with the post
    private List<String> tags = new ArrayList<>(); // List of tags associated with the post
    private String createdAt; // Timestamp for when the post was created
    private String updatedAt; // Timestamp for when the post was last updated

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getImages() {
        return imageIds;
    }

    public void setImages(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static PostDetail toPostDetail(Post post) {
        PostDetail dto = new PostDetail();
        dto.setPostId(post.getId());
        dto.setUserId(post.getOwner().getId());
        dto.setBody(post.getBody());
        dto.setImages(post.getImages().stream().map(Image::getId).collect(Collectors.toList()));
        dto.setTags(post.getTags().stream().map(PostTag::getName).collect(Collectors.toList()));
        dto.setCreatedAt(post.getCreated().toString());
        return dto;
    }
}