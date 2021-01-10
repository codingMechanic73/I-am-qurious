package com.example.qurious.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * This class is used to do validations
 */
@Component
public class Validator {

    public boolean checkIfImage(MultipartFile file) {
        return Objects
                .requireNonNull(file.getContentType())
                .startsWith("image/");
    }

}
