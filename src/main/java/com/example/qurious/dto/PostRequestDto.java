package com.example.qurious.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    private String postTitle;
    private String postDescription;
    private Long topicId;

}
