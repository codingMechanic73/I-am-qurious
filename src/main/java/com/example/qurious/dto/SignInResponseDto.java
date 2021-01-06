package com.example.qurious.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SignInResponseDto {

    private String userName;
    private String role;
    private String jwtToken;
    private String profileUrl;

}
