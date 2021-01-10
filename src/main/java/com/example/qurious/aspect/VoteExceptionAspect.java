package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.AlreadyVotedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles vote related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class VoteExceptionAspect {

    /**
     * This is an exception handling if userName has already vote in same direction
     *
     * @param exception returned by the controller
     * @return response stating that there is a conflict
     */
    @ExceptionHandler(AlreadyVotedException.class)
    public ResponseEntity<CustomResponseDto> handleAlreadyVotedException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
