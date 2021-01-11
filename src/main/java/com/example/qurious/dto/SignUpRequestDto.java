package com.example.qurious.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @ApiModelProperty(
            value = "userName which uniquely identify you",
            dataType = "String",
            example = "codingMechanic73",
            required = true)
    private String userName;
    @ApiModelProperty(
            value = "password corresponding to the userName",
            dataType = "String",
            example = "Password_for_codingMechanic73",
            required = true)
    @ToString.Exclude
    private String password;
    @ApiModelProperty(
            value = "First Name",
            dataType = "String",
            example = "Alan",
            required = true)
    private String firstName;
    @ApiModelProperty(
            value = "Last Name",
            dataType = "String",
            example = "Winston",
            required = false)
    private String lastName;
    @ApiModelProperty(
            value = "email",
            dataType = "String",
            example = "alanwinston2020@gmail.com",
            required = true)
    private String email;
    @ApiModelProperty(
            value = "Profile url",
            dataType = "String",
            example = "default_profile.jpg",
            required = false)
    private String profileUrl;

}
