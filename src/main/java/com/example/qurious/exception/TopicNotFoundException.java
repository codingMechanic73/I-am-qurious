package com.example.qurious.exception;

public class TopicNotFoundException extends Exception{

    public TopicNotFoundException(String topicName) {
        super("Topic with id " + topicName + " not found");
    }

}
