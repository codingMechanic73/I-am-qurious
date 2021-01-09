package com.example.qurious.controller;

import com.example.qurious.dto.*;
import com.example.qurious.exception.CriticalException;
import com.example.qurious.exception.UserAlreadyExistsException;
import com.example.qurious.exception.UserNameAlreadyExistsException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.service.AuthService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * This class provides authorization related methods
 */
@Data
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Sign the user in
     *
     * @param signInRequestDto credentials from user
     * @return user details with valid jwt token
     */
    @PostMapping(value = "signin")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = authService.signIn(signInRequestDto);
        return ResponseEntity
                .ok()
                .body(signInResponseDto);
    }

    /**
     * Sign up the user
     *
     * @param signUpRequestDto details to create a new user
     * @return success response stating user created
     * @throws UserAlreadyExistsException     if the user already exist
     * @throws UserNameAlreadyExistsException if the userName is already in use
     * @throws CriticalException              Internal server error
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<CustomResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto)
            throws UserNameAlreadyExistsException,
            UserAlreadyExistsException, CriticalException {
        authService.signUp(signUpRequestDto);
        CustomResponseDto customResponseDto = CustomResponseDto.builder()
                .message("Signed Up Successfully")
                .timestamp(Instant.now())
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity
                .ok()
                .body(customResponseDto);
    }

    /**
     * Checks if the userName is in user
     *
     * @param userNameDto entered userName
     * @return if userName is available or not
     * @throws UserNameAlreadyExistsException if user already exists
     */
    @PostMapping(value = "/checkusernameexists")
    public ResponseEntity<CustomResponseDto> checkUserNameExists(@RequestBody UserNameDto userNameDto)
            throws UserNameAlreadyExistsException {
        if (authService.checkUserNameExists(userNameDto.getUserName())) {
            throw new UserNameAlreadyExistsException(userNameDto.getUserName());
        }
        CustomResponseDto customResponseDto = CustomResponseDto
                .builder()
                .message("available")
                .timestamp(Instant.now())
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(customResponseDto);
    }

}
