package com.example.qurious.service;

import com.example.qurious.dto.SignInRequestDto;
import com.example.qurious.dto.SignInResponseDto;
import com.example.qurious.dto.SignUpRequestDto;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.exception.CriticalException;
import com.example.qurious.exception.UserAlreadyExistsException;
import com.example.qurious.exception.UserNameAlreadyExistsException;
import com.example.qurious.repository.UserDetailsRepository;
import com.example.qurious.repository.UserRepository;
import com.example.qurious.security.JwtProvider;
import com.example.qurious.util.EntityDtoMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class that provides authentication related service
 */
@Data
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final MyUserServiceDetails userServiceDetails;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

//    As EntityDtoMapper is used in the authService and
//    AuthService is used in EntityDtoMapper it created circular
//    dependency and hence constructor injection is not possible.
//    EntityDtoMapper is injected using field injection and lazily initialized

    @Autowired
    @Lazy
    private EntityDtoMapper entityDtoMapper;

    /**
     * Sign the user in
     *
     * @param signInRequestDto credentials from user
     * @return user details with valid jwt token
     */
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Authentication authenticatedUser = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getUserName(),
                        signInRequestDto.getPassword()));

        String jwtToken = jwtProvider.generateToken(authenticatedUser.getName());
        UserEntity user = userRepository
                .findByUserName(authenticatedUser.getName())
                .orElseThrow(() -> new UsernameNotFoundException(authenticatedUser.getName()));

        return SignInResponseDto.builder()
                .userName(user.getUserName())
                .role(user.getRole().getRoleType())
                .jwtToken(jwtToken)
                .profileUrl(user.getUserDetails().getProfileUrl())
                .build();
    }

    /**
     * Sign up the user
     *
     * @param signUpRequestDto details to create a new user
     * @throws UserAlreadyExistsException     if the user already exist
     * @throws UserNameAlreadyExistsException if the userName is already in use
     * @throws CriticalException              Internal server error
     */
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) throws
            UserNameAlreadyExistsException,
            UserAlreadyExistsException,
            CriticalException {
        if (checkUserNameExists(signUpRequestDto.getUserName())) {
            throw new UserNameAlreadyExistsException(signUpRequestDto.getUserName());
        }

        if (checkEmailExists(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException(signUpRequestDto.getEmail());
        }

        UserEntity user = entityDtoMapper.signUpRequestDtoToUserEntity(signUpRequestDto);
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        userRepository.save(user);
    }

    /**
     * Get user from the security context which was authenticated in jwt filter
     *
     * @return user
     */
    @Transactional(readOnly = true)
    public UserEntity getCurrentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }

    /**
     * Checks if the userName exists
     *
     * @param userName user input
     * @return true or false depending if the user exists or not
     */
    @Transactional(readOnly = true)
    public boolean checkUserNameExists(String userName) {
        return userRepository
                .findByUserName(userName)
                .isPresent();
    }

    /**
     * Checks if the email exists
     *
     * @param email email of the user
     * @return true or false depending if the email exists or not
     */
    @Transactional(readOnly = true)
    public boolean checkEmailExists(String email) {
        return userDetailsRepository
                .findByEmail(email)
                .isPresent();
    }

}
