package com.example.qurious.exception;


public class PostNotFoundException extends Exception {

    public PostNotFoundException(String postId) {
        super("Post with id " + postId + " not found");
    }

}
