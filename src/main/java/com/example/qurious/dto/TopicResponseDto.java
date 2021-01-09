package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicResponseDto {

    private Long topicId;
    private String topicName;
    private String topicDescription;
    private Integer noOfPosts;
    private String createdTimeAgo;
    private String createdBy;
    private String topicPicture;

}
