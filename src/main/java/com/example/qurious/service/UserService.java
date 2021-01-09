package com.example.qurious.service;

import com.example.qurious.entity.UserEntity;
import com.example.qurious.exception.FileNotFoundException;
import com.example.qurious.exception.FileStorageException;
import com.example.qurious.repository.UserRepository;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

/**
 * Class that provides user related service
 */
@Data
@Service
public class UserService {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final FileStorageService fileStorageService;

    /**
     * used to upload or change the user profile picture
     *
     * @param profilePicture the image user inputs
     * @throws FileStorageException invalid image related exceptions
     * @throws IOException          server or hardware related exceptions
     */
    public void updateProfile(MultipartFile profilePicture) throws IOException, FileStorageException {
        UserEntity user = authService.getCurrentUser();
        String extension = Objects.requireNonNull(profilePicture.getContentType()).split("/")[1];
        String fileName = user.getUserName() + "." + extension;
        fileStorageService.storeFile(profilePicture, "profile/", fileName);
        user.getUserDetails().setProfileUrl(fileName);
    }

    /**
     * Load images based on user
     *
     * @return actual image as a resource
     * @throws FileNotFoundException exception if the image is not found
     * @throws MalformedURLException exception if  image is not retrieved because of wrong filepath
     */
    public Resource getProfile() throws FileNotFoundException, MalformedURLException {
        UserEntity user = authService.getCurrentUser();
        String fileName = user.getUserDetails().getProfileUrl();
        return fileStorageService.loadFileAsResource("profile", fileName);
    }
}
