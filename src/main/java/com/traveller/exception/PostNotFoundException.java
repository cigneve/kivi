package com.traveller.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Integer postId) {
        super("Post with id " + postId + " not found");
    }

}
