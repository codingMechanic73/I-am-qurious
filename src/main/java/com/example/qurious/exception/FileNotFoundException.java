package com.example.qurious.exception;

public class FileNotFoundException extends Exception {

    public FileNotFoundException(String fileName) {
        super("No file found with name " + fileName);
    }

}
