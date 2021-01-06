package com.example.qurious.exception;

public class UserNameAlreadyExistsException extends Exception {

    public UserNameAlreadyExistsException(String userName) {
        super(userName + " is not available");
    }
}
