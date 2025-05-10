package com.traveller.kivi.model.posts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO
 */
public class PostDetail {

    public Integer postId; // Unique identifier for the post
    public Integer userId; // ID of the user who created the post
    public String body; // Content of the post
    public Integer imageId; // List of image URLs associated with the post
    public List<String> tags = new ArrayList<>(); // List of tags associated with the post
    public LocalDate createdAt; // Timestamp for when the post was created
    public Integer likeCount;

    public static PostDetail toPostDetail(Post post) {
        PostDetail dto = new PostDetail();
        dto.postId = post.getId();
        dto.userId = post.getOwner().getId();
        dto.body = post.getBody();
        dto.imageId = post.getImage() != null ? post.getImage().getId() : null;
        dto.tags = post.getTags().stream().map(PostTag::getName).collect(Collectors.toList());
        dto.createdAt = post.getCreated();
        dto.likeCount = post.getLikers().size();
        return dto;
    }
}