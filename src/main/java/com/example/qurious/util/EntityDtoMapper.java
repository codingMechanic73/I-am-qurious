package com.example.qurious.util;

import com.example.qurious.dto.SignUpRequestDto;
import com.example.qurious.entity.RoleEntity;
import com.example.qurious.entity.UserDetailsEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.repository.RoleRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Utility class used to map dto and entity
 */
@Data
@Component
public class EntityDtoMapper {

    private final RoleRepository roleRepository;

    public UserEntity SignUpRequestDtoToUserEntity(SignUpRequestDto signUpRequestDto) {
        UserEntity user = new UserEntity();
        user.setUserName(signUpRequestDto.getUserName());

        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setEmail(signUpRequestDto.getEmail());
        userDetailsEntity.setProfileUrl(signUpRequestDto.getProfileUrl());
        userDetailsEntity.setCreatedAt(Instant.now());

        RoleEntity role = roleRepository.getOne(2L);

        user.setRole(role);
        user.setUserDetails(userDetailsEntity);
        user.setVerified(true);
        return user;
    }

}
