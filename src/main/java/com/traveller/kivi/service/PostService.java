package com.traveller.kivi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.UserNotFoundException;
import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.posts.PostDTO;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPost(Integer postId) {
        return postRepository.findById(postId).get();
    }

    public Page<Post> getPaginatedPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * Maps a PostDTO to a Post and adds it to the repository.
     * 
     * @param postDTO
     * @return created post
     */
    public Post createPostFromDTO(PostDTO postDTO) {
        if (!userService.userExistsById(postDTO.getUserId())) {
            throw new UserNotFoundException("User with ID " + postDTO.getUserId() + " not found");
        }
        User owner = userService.getUserById(postDTO.getUserId());
        Post constructPost = new Post(owner, postDTO.getBody(), postDTO.getImages());
        constructPost.setBody(postDTO.getBody());
        return postRepository.save(constructPost);
    }
}
