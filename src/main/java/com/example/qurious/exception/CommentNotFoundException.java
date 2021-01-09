package com.example.qurious.exception;

public class CommentNotFoundException extends Exception {

    public CommentNotFoundException(String commentId) {
        super("Comment with id " + commentId + " not found");
    }

}
