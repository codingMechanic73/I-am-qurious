package com.example.qurious.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String email) {
        super("user with email " + email + " already exists");
    }

}
