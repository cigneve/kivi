package com.traveller.kivi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.posts.PostCreateDTO;
import com.traveller.kivi.model.posts.PostDetail;
import com.traveller.kivi.service.PostService;

import jakarta.validation.Valid;

@Controller
@RestController
@RequestMapping("/api/posts")
@Valid
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDetail> createPost(@Valid @ModelAttribute PostCreateDTO postDTO) {
        if (postDTO.image != null && postDTO.image.isEmpty()) {
            postDTO.image = null;
        }
        PostDetail createdPost = postService.createPostFromDTO(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<PostDetail> getPost(@PathVariable Integer postId) {
        PostDetail createdPost = postService.getPostDetail(postId);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{postId}/photo")
    public Resource getPostPhoto(@PathVariable Integer postId) {
        return postService.getPostImage(postId);
    }

    @GetMapping("/feed")
    public PagedModel<PostDetail> getPaginatedPosts(Pageable pageable, @RequestParam(required = false) Integer userId) {
        if (userId == null) {
            return new PagedModel<>(postService.getAllPosts(pageable));
        } else {
            return new PagedModel<>(postService.getPostsOfOthers(pageable, userId));
        }
    }

    @GetMapping("/{userId}")
    public List<PostDetail> getPaginatedPostsOfUser(@PathVariable Integer userId) {
        return postService.getPostsOfUser(userId);
    }

    @PostMapping("/updatetags/{postId}")
    public PostDetail updatePostTags(@PathVariable Integer postId, @RequestBody List<String> tags) {
        return postService.updatePostTags(postId, tags);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostDetail> likePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId) {
        PostDetail dto = postService.likePost(postId, userId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<PostDetail> unlikePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId) {
        PostDetail dto = postService.unlikePost(postId, userId);
        return ResponseEntity.ok(dto);
    }

}
