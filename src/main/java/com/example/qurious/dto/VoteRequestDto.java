package com.example.qurious.dto;

import com.example.qurious.enums.VoteTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDto {

    @ApiModelProperty(
            value = "Post Id",
            dataType = "Long",
            example = "1",
            required = true)
    private Long postId;
    @ApiModelProperty(
            value = "Type of vote",
            required = true)
    private VoteTypeEnum voteType;

}
