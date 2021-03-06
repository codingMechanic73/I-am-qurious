package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    @ApiModelProperty(
            value = "userName of the user",
            dataType = "String",
            example = "codingMechanic73",
            required = true)
    private String userName;
    @ToString.Exclude
    @ApiModelProperty(
            value = "password corresponding to the userName",
            dataType = "String",
            example = "Password_for_codingMechanic73",
            required = true)
    private String password;

}
