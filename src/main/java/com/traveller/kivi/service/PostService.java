package com.traveller.kivi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.UserNotFoundException;
import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.posts.PostCreateDTO;
import com.traveller.kivi.model.posts.PostTag;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.PostRepository;
import com.traveller.kivi.repository.PostTagRepository;

@Service
public class PostService {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPost(Integer postId) {
        return postRepository.findById(postId).get();
    }

    public Page<Post> getPostsOfOthers(Pageable pageable, Integer userId) {
        User user = userService.getUserById(userId);
        return postRepository.findByOwnerNot(pageable, user);
    }

    public List<Post> getPostsOfUser(Integer userId) {
        User user = userService.getUserById(userId);
        return postRepository.findByOwner(user);
    }

    /**
     * Maps a PostDTO to a Post and adds it to the repository.
     * 
     * @param postDTO
     * @return created post
     */
    public Post createPostFromDTO(PostCreateDTO postDTO) {
        if (!userService.userExistsById(postDTO.getUserId())) {
            throw new UserNotFoundException("User with ID " + postDTO.getUserId() + " not found");
        }
        User owner = userService.getUserById(postDTO.getUserId());
        Post constructPost = new Post(owner, postDTO.getBody(), new ArrayList<>());
        for (String image : postDTO.getImages()) {
            String imageId = new StringBuilder().append(owner.getId()).append(UUID.randomUUID().toString()).toString();
            Image imageObj = imageService.createImage(imageId, image);
            constructPost.getImages().add(imageObj);
        }
        constructPost.setBody(postDTO.getBody());
        // Handle tags
        if (postDTO.getTags() != null) {
            postDTO.getTags().forEach(tagName -> {
                PostTag tag = postTagRepository.findByName(tagName)
                        .orElseGet(() -> postTagRepository.save(new PostTag(tagName)));
                constructPost.getTags().add(tag);
            });
        }
        return postRepository.save(constructPost);
    }

    /**
     * Updates the tags of an existing post.
     * 
     * @param postId
     * @param tags
     * @return updated post
     */
    public Post updatePostTags(Integer postId, List<String> tags) {
        Post post = getPost(postId);
        post.getTags().clear();
        tags.forEach(tagName -> {
            PostTag tag = postTagRepository.findByName(tagName)
                    .orElseGet(() -> postTagRepository.save(new PostTag(tagName)));
            post.getTags().add(tag);
        });
        return postRepository.save(post);
    }
}
