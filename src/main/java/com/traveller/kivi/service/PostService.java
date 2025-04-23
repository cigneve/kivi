package com.traveller.kivi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveller.kivi.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

}
