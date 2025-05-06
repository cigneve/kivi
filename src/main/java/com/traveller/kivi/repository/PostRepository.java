package com.traveller.kivi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.posts.Post;
import com.traveller.kivi.model.users.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByOwnerNot(Pageable pageable, User user);

    List<Post> findByOwner(User user);

    Long countByOwner_Id(Integer ownerId);
}