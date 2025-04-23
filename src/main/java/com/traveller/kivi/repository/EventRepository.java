package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.posts.Post;

@Repository
public interface EventRepository extends JpaRepository<Post, Integer> {
}