package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @ApiModelProperty(
            value = "Comment for the Post with Id 1",
            dataType = "String",
            example = "What is the guarantee that the alternatives will respect the privacy of its users?",
            required = true)
    private String comment;
    @ApiModelProperty(
            value = "Post Id",
            dataType = "Long",
            example = "1",
            required = true)
    private Long postId;

}
