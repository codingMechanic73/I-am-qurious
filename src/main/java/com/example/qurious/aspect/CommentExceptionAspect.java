package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles Comment related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class CommentExceptionAspect {

    /**
     * Exception handling if Comment is not found
     *
     * @param exception returned by the controller
     * @return response stating that resource is not found
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<CustomResponseDto> handleCommentNotFoundException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
