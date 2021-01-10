package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostResponseDto {

    private Long postId;
    private Long topicId;
    private String topicName;
    private String postTitle;
    private String postDescription;
    private String postedBy;
    private Instant postedOn;
    private Integer commentCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private boolean isLiked;
    private boolean isDisliked;

}
