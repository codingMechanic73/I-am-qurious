package com.example.qurious.exception;

public class FileStorageException extends Exception {

    public FileStorageException(String fileName) {
        super(fileName + " is not a valid image");
    }

}
