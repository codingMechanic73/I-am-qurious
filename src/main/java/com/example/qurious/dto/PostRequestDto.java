package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    @ApiModelProperty(
            value = "Post for the Topic with Id 1",
            dataType = "String",
            example = "Do you think it's right time to find alternatives for whatsApp?",
            required = true)
    private String postTitle;
    @ApiModelProperty(
            value = "Post Description for the Topic with Id 1",
            dataType = "String",
            example = "Do you think it's the right time to find alternatives for whatsApp?",
            required = true)
    private String postDescription;
    @ApiModelProperty(
            value = "Topic Id",
            dataType = "Long",
            example = "1",
            required = true)
    private Long topicId;

}
