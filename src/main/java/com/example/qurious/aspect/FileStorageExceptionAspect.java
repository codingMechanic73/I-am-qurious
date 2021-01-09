package com.example.qurious.aspect;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.FileNotFoundException;
import com.example.qurious.exception.FileNotSavedException;
import com.example.qurious.exception.FileStorageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.MalformedURLException;
import java.time.Instant;

/**
 * This class handles FileStorage related exceptions using aspect oriented programing
 */
@RestControllerAdvice
public class FileStorageExceptionAspect {

    /**
     * Exception handling if File is not an image or is not proper
     *
     * @param exception returned by the controller
     * @return response stating that there was an invalid request
     */
    @ExceptionHandler({FileStorageException.class,
            FileNotSavedException.class,
            MalformedURLException.class})
    public ResponseEntity<CustomResponseDto> handleFileStorageExceptions(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handling if file is not found
     *
     * @param exception returned by the controller
     * @return response stating that resource is not found
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<CustomResponseDto> handleFileNotFoundExceptions(Exception exception) {
        CustomResponseDto response = CustomResponseDto
                .builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
