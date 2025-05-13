package com.traveller.kivi.service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.traveller.exception.PostNotFoundException;
import com.traveller.kivi.model.Image;
import com.traveller.kivi.model.achievements.CriterionType;
import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.posts.PostCreateDTO;
import com.traveller.kivi.model.posts.PostDetail;
import com.traveller.kivi.model.posts.PostTag;
import com.traveller.kivi.model.users.User;
import com.traveller.kivi.model.users.UserDetail;
import com.traveller.kivi.repository.PostRepository;
import com.traveller.kivi.repository.PostTagRepository;

@Service
@Transactional
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
        Long totalPosts = postRepository.countByOwner_Id(post.getOwner().getId());
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
     * Returns all posts.
     * Used as a feed implementation
     * 
     * @param pageable
     * @param userId
     * @return
     */
    public Page<PostDetail> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDetail::toPostDetail);
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
    public PostDetail createPostFromDTO(PostCreateDTO postDTO) {
        Post constructPost = mapDTO(postDTO);

        return PostDetail.toPostDetail(createPost(constructPost));
    }

    private Post mapDTO(PostCreateDTO postDTO) {
        User owner = userService.getUserById(postDTO.userId);
        Post constructPost = new Post(owner, postDTO.body, null);
        if (postDTO.image != null) {
            MultipartFile image = postDTO.image;
            InputStream stream;
            try {
                stream = image.getInputStream();
            } catch (Exception e) {
                throw new RuntimeException("Error getting image from PostCreateDTO");
            }
            Image im = imageService.createImage(stream);
            constructPost.setImage(im);

        }
        // Handle tags
        if (postDTO.tags != null)

        {
            postDTO.tags.forEach(tagName -> {
                PostTag tag = postTagRepository.findByName(tagName)
                        .orElseGet(() -> postTagRepository.save(new PostTag(tagName)));
                constructPost.getTags().add(tag);
            });
        }
        return constructPost;
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

    public Resource getPostImage(Integer postId) {
        Post post = getPostById(postId);
        return imageService.getImageContentAsResource(post.getImage());
    }

    public List<PostDetail> getAllPosts() {
        return postRepository.findAll().stream().map(PostDetail::toPostDetail).collect(Collectors.toList());
    }

    public List<UserDetail> getPostLikers(Integer postId) {
        return getPostById(postId).getLikers().stream().map(UserDetail::fromUser).toList();
    }

}
