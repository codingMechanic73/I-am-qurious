package com.example.qurious.exception;

public class AlreadyVotedException extends Exception {

    public AlreadyVotedException(String voteType) {
        super("You have already " + voteType);
    }

}
