package com.example.qurious.service;

import com.example.qurious.entity.RoleEntity;
import com.example.qurious.entity.UserDetailsEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.repository.RoleRepository;
import com.example.qurious.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * Used to initialize the data when program starts
 */
@Data
@Service
public class InitService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    private final String uploadDir = "storage/assets/";

    @PostConstruct
    public void initData() throws IOException {
        addRoles();
        addAdmin();
        createFolders("/profile");
    }

    public void addRoles() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRoleId(1L);
        adminRole.setRoleType("ADMIN");
        roleRepository.save(adminRole);

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleId(2L);
        userRole.setRoleType("USER");
        roleRepository.save(userRole);
    }

    public void addAdmin() {
        UserEntity admin = new UserEntity();
        admin.setUserName("admin");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(roleRepository.getOne(1L));
        admin.setVerified(true);

        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setEmail(adminEmail);
        userDetails.setFirstName("alan");
        userDetails.setCreatedOn(Instant.now());
        userDetails.setProfileUrl("");
        admin.setUserDetails(userDetails);
        userRepository.save(admin);
    }

    public void createFolders(String folder) throws IOException {
        Path uploadPath = Paths.get(uploadDir + folder).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

}
