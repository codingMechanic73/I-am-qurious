package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String comment;
    private Long postId;
    private String postName;
    private Instant commentedOn;
    private String commentedBy;

}
