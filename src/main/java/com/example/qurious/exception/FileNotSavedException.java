package com.example.qurious.exception;

public class FileNotSavedException extends RuntimeException {

    public FileNotSavedException(String fileName) {
        super(fileName + " cannot be saved");
    }

}
