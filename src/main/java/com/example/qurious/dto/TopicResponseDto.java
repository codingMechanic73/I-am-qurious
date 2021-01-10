package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TopicResponseDto {

    private Long topicId;
    private String topicName;
    private String topicDescription;
    private Integer noOfPosts;
    private Instant createdOn;
    private String createdBy;
    private String topicPicture;

}
