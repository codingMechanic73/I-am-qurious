package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    private String userName;
    private String role;
    private String jwtToken;
    private String profileUrl;

}
