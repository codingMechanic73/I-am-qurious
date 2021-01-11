package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameRequestDto {

    @ApiModelProperty(
            value = "user name",
            dataType = "String",
            example = "codingMechanic73",
            required = true)
    private String userName;

}
