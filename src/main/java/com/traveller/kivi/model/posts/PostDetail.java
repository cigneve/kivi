package com.traveller.kivi.model.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.traveller.kivi.model.Image;

/**
 * DTO
 */
public class PostDetail {

    public Integer postId; // Unique identifier for the post
    public Integer userId; // ID of the user who created the post
    public String body; // Content of the post
    public List<String> imageIds = new ArrayList<>(); // List of image URLs associated with the post
    public List<String> tags = new ArrayList<>(); // List of tags associated with the post
    public String createdAt; // Timestamp for when the post was created
    public String updatedAt; // Timestamp for when the post was last updated
    public Integer likeCount;

    public static PostDetail toPostDetail(Post post) {
        PostDetail dto = new PostDetail();
        dto.postId = post.getId();
        dto.userId = post.getOwner().getId();
        dto.body = post.getBody();
        dto.imageIds = post.getImages().stream().map(Image::getId).collect(Collectors.toList());
        dto.tags = post.getTags().stream().map(PostTag::getName).collect(Collectors.toList());
        dto.createdAt = post.getCreated().toString();
        dto.likeCount = post.getLikers().size();
        return dto;
    }
}