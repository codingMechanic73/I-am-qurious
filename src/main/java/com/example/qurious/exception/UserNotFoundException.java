package com.example.qurious.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String userName) {
        super("No user found with username: " + userName);
    }

}
