package com.example.qurious.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @ToString.Exclude
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String profileUrl;

}
