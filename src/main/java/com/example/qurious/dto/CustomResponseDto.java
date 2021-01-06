package com.example.qurious.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CustomResponseDto {

    private Instant timestamp;
    private String message;
    private int statusCode;

}
