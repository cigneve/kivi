package com.traveller.kivi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traveller.exception.PostNotFoundException;
import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.posts.PostCreateDTO;
import com.traveller.kivi.model.posts.PostDetail;
import com.traveller.kivi.model.posts.PostTag;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.repository.PostRepository;
import com.traveller.kivi.repository.PostTagRepository;

import com.traveller.kivi.service.AchievementService;
import com.traveller.kivi.model.achievements.CriterionType;

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

    @Autowired
    private AchievementService achievementService;

    public Post createPost(Post post) {
        Post saved = postRepository.save(post);
        int totalPosts = postRepository.countByOwner_Id(post.getOwner().getId());
        achievementService.checkAndAward(
                post.getOwner().getId(),
                CriterionType.POST_CREATE.name(),
                totalPosts);

        return saved;
    }

    public PostDetail getPostDetail(Integer postId) {
        return PostDetail.toPostDetail(getPostById(postId));
    }

    /**
     * Returns a post with the Id.
     * Use {@literal PostDetail} for exposed api.
     * 
     * @param postId
     * @return
     */
    public Post getPostById(Integer postId) {
        try {
            return postRepository.findById(postId).get();
        } catch (Exception e) {
            throw new PostNotFoundException(postId);
        }
    }

    /**
     * Returns the posts of users excluding the userId.
     * Used as a feed implementation
     * 
     * @param pageable
     * @param userId
     * @return
     */
    public Page<PostDetail> getPostsOfOthers(Pageable pageable, Integer userId) {
        User user = userService.getUserById(userId);
        return postRepository.findByOwnerNot(pageable, user).map(PostDetail::toPostDetail);
    }

    public List<PostDetail> getPostsOfUser(Integer userId) {
        User user = userService.getUserById(userId);
        return postRepository.findByOwner(user).stream().map(PostDetail::toPostDetail).collect(Collectors.toList());
    }

    /**
     * Maps a PostDTO to a Post and adds it to the repository.
     * 
     * @param postDTO
     * @return created post
     */
    public Post createPostFromDTO(PostCreateDTO postDTO) {
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
    public PostDetail updatePostTags(Integer postId, List<String> tags) {
        Post post = getPostById(postId);
        post.getTags().clear();
        tags.forEach(tagName -> {
            PostTag tag = postTagRepository.findByName(tagName)
                    .orElseGet(() -> postTagRepository.save(new PostTag(tagName)));
            post.getTags().add(tag);
        });
        return PostDetail.toPostDetail(postRepository.save(post));
    }

    public PostDetail likePost(Integer postId, Integer userId) {
        User user = userService.getUserById(userId);
        Post post = getPostById(postId);

        // Check if the user has already liked the post
        if (!post.getLikers().contains(user)) {
            post.getLikers().add(user);
        }
        return PostDetail.toPostDetail(post);
    }

    public PostDetail unlikePost(Integer postId, Integer userId) {
        User user = userService.getUserById(userId);
        Post post = getPostById(postId);

        // Check if the user has already liked the post
        if (post.getLikers().contains(user)) {
            post.getLikers().remove(user);
        }

        return PostDetail.toPostDetail(post);
    }
}
