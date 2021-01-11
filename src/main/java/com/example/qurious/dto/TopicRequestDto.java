package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequestDto {

    @ApiModelProperty(
            value = "Topic Name",
            dataType = "String",
            example = "Technology",
            required = true)
    private String topicName;
    @ApiModelProperty(
            value = "Topic description",
            dataType = "String",
            example = "Any posts related to technology can be added here!!",
            required = true)
    private String topicDescription;

}
