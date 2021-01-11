package com.example.qurious.controller;

import com.example.qurious.dto.*;
import com.example.qurious.exception.CriticalException;
import com.example.qurious.exception.UserAlreadyExistsException;
import com.example.qurious.exception.UserNameAlreadyExistsException;
import com.example.qurious.service.AuthService;
import io.swagger.annotations.*;
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
@Api(description = "Endpoints for Sign Up, Sign In, Check if username exists")
public class AuthController {

    private final AuthService authService;

    /**
     * Sign the user in
     *
     * @param signInRequestDto credentials from user
     * @return user details with valid jwt token
     */
    @ApiOperation(value = "Sign the user In")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signed In Successfully"),
            @ApiResponse(code = 403, message = "You are not authenticated"),
            @ApiResponse(code = 409, message = "The resource you were trying to add is already in use"),
    })
    @PostMapping(value = "signin")
    public ResponseEntity<SignInResponseDto> signIn(
            @ApiParam(value = "Sign In Request", required = true, example = "sent")
            @RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = authService.signIn(signInRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
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
    @ApiOperation(value = "SignUp the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signed Up Successfully"),
            @ApiResponse(code = 409, message = "The resource you were trying to add is already in use"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
                .status(HttpStatus.CREATED)
                .body(customResponseDto);
    }

    /**
     * Checks if the userName is in user
     *
     * @param userNameRequestDto entered userName
     * @return if userName is available or not
     * @throws UserNameAlreadyExistsException if user already exists
     */
    @ApiOperation(value = "Check if user exists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UserName is available"),
            @ApiResponse(code = 409, message = "The resource you were trying to add is already in use"),
    })
    @PostMapping(value = "/checkusernameexists")
    public ResponseEntity<CustomResponseDto> checkUserNameExists(@RequestBody UserNameRequestDto userNameRequestDto)
            throws UserNameAlreadyExistsException {
        if (authService.checkUserNameExists(userNameRequestDto.getUserName())) {
            throw new UserNameAlreadyExistsException(userNameRequestDto.getUserName());
        }
        CustomResponseDto customResponseDto = CustomResponseDto
                .builder()
                .message("available")
                .timestamp(Instant.now())
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customResponseDto);
    }

}
