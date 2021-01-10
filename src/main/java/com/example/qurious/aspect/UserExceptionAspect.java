package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.UserAlreadyExistsException;
import com.example.qurious.exception.UserNameAlreadyExistsException;
import com.example.qurious.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * This class handles User related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class UserExceptionAspect {

    /**
     * This is an exception handling if user already exists in the system
     *
     * @param exception returned by the controller
     * @return response stating that there is a conflict and resource already exists
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomResponseDto> handleUserAlreadyExistsException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }

    /**
     * This is an exception handling if userName is already in use
     *
     * @param exception returned by the controller
     * @return response stating that there is a conflict and resource is already in use
     */
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<CustomResponseDto> handleUserNameAlreadyExistsException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * This is an exception handling if userName is not found
     *
     * @param exception returned by the controller
     * @return response stating user not found
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomResponseDto> handleUserNotFoundException(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
