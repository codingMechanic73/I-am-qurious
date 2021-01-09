package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.CriticalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles Server related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class CriticalExceptionAspect {

    @ExceptionHandler(CriticalException.class)
    public ResponseEntity<CustomResponseDto> handleCriticalException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message("Something went wrong!")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
