package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.TopicAlreadyExistsException;
import com.example.qurious.exception.TopicNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles Topic related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class TopicExceptionAspect {

    /**
     * Exception handling if Topic already exists in the system
     *
     * @param exception returned by the controller
     * @return response stating that there is a conflict and resource already exists
     */
    @ExceptionHandler(TopicAlreadyExistsException.class)
    public ResponseEntity<CustomResponseDto> handleTopicAlreadyExistsException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Exception handling if Topic is not found
     *
     * @param exception returned by the controller
     * @return response stating that resource is not found
     */
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<CustomResponseDto> handleTopicNotFoundException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
