package com.example.qurious.exception;

public class TopicAlreadyExistsException extends Exception {

    public TopicAlreadyExistsException(String topicName) {
        super("Topic with topic name " + topicName + " already exists");
    }

}
