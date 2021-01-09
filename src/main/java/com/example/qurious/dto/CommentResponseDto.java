package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String comment;
    private Long postId;
    private String postName;
    private String commentedTimeAgo;
    private String commentedBy;

}
