package com.traveller.kivi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.posts.PostTag;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    Optional<PostTag> findByName(String name);
}
