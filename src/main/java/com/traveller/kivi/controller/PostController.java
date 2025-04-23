package com.traveller.kivi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Valid
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Integer postId) {
        Post createdPost = postService.getPost(postId);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/feed")
    public PagedModel<Post> getPaginatedPosts(Pageable pageable) {
        return new PagedModel<>(postService.getPaginatedPosts(pageable));
    }
}
