package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles Post related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class PostExceptionAspect {

    /**
     * Exception handling if Post is not found
     *
     * @param exception returned by the controller
     * @return response stating that resource is not found
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CustomResponseDto> handlePostNotFoundException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
