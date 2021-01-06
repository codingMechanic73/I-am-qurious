package com.example.qurious.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    @ToString.Exclude
    private String password;
    private String userName;

}
