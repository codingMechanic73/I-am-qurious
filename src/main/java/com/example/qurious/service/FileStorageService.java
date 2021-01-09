package com.example.qurious.service;

import com.example.qurious.exception.FileNotFoundException;
import com.example.qurious.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Class that provides storage related service
 */
@Service
public class FileStorageService {

    private final String uploadDir = "storage/assets/";
    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
    }

    /**
     * Stores file inside the uploadDir inside a given folder
     *
     * @param file       image to be stored
     * @param folderName folder inside the uploadDir
     * @param fileName   save with specified fileName
     * @return name of the file
     * @throws FileStorageException invalid image related exceptions
     * @throws IOException          server or hardware related exceptions
     */
    public String storeFile(MultipartFile file, String folderName, String fileName)
            throws FileStorageException,
            IOException {

        Path uploadPath = Paths.get(uploadDir + folderName).toAbsolutePath().normalize();

        if (fileName.contains("..")) {
            throw new FileStorageException(fileName);
        }

        Path targetLocation = this.fileStorageLocation.resolve(uploadPath + "/" + fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;

    }

    /**
     * Load images based on fileName
     *
     * @param folderName Name of the folder where images are stored
     * @param fileName   Name of the image
     * @return actual image as a resource
     * @throws FileNotFoundException exception if the image with the given name is not found
     * @throws MalformedURLException exception if  image is not retrieved because of wrong filepath
     */
    public Resource loadFileAsResource(String folderName, String fileName)
            throws FileNotFoundException, MalformedURLException {

        Path filePath = this.fileStorageLocation.resolve(folderName + "/" + fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

}
