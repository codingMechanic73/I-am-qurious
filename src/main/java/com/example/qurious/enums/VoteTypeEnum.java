package com.example.qurious.enums;

import java.util.Arrays;


public enum VoteTypeEnum {

    UP_VOTE(1),
    DOWN_VOTE(-1);

    int direction;

    VoteTypeEnum(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    /**
     * Returns the vote name based on direction
     *
     * @param direction integer value of 1 or -1
     * @return upvote or downvote
     * @throws Exception invalid direction provided
     */
    public VoteTypeEnum lookUp(int direction) throws Exception {
        return Arrays.stream(VoteTypeEnum.values())
                .filter(vote -> vote.getDirection() == direction)
                .findAny()
                .orElseThrow(() -> new Exception("Invalid Vote"));
    }
}
