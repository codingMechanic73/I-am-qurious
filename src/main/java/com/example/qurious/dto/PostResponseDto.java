package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {

    private Long postId;
    private Long topicId;
    private String topicName;
    private String postTitle;
    private String postDescription;
    private String postedBy;
    private String postedTimeAgo;
    private Integer commentCount;
    private Integer voteCount;
    private boolean isUpVoted;
    private boolean isDownVoted;

}
