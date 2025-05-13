package com.traveller.kivi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.users.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByOwnerNot(Pageable pageable, User user);

    List<Post> findByOwner(User user);

    Long countByOwner_Id(Integer ownerId);

    /**
     * Total number of likes (across all posts)
     */
    @Query("SELECT COUNT(l) FROM Post p JOIN p.likers l WHERE p.owner.id = :ownerId")
    Long countLikesByOwner_Id(@Param("ownerId") Integer ownerId);

    /**
     * Returns the number of images uploaded by the user in all their posts.
     */
    @Query("SELECT COUNT(p) FROM Post p WHERE p.owner.id = :ownerId")
    Long countPostsByOwner_Id(@Param("ownerId") Integer ownerId);
}