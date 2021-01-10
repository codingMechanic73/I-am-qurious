package com.example.qurious.controller;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.service.UserService;
import com.example.qurious.util.Validator;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;

@Data
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final Validator validator;

    @PutMapping("/changeprofilepicture")
    public ResponseEntity<CustomResponseDto> changeDisplayPicture(@RequestParam("profilePicture") MultipartFile profilePicture) throws Exception {

        System.out.println(profilePicture.getContentType());
        if (validator.checkIfImage(profilePicture)) {
            userService.updateProfile(profilePicture);
            CustomResponseDto customResponseDto = CustomResponseDto.builder()
                    .message("Profile picture was updated successfully")
                    .timestamp(Instant.now())
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseEntity
                    .ok()
                    .body(customResponseDto);
        } else {
            CustomResponseDto customResponseDto = CustomResponseDto.builder()
                    .message("Not a valid image")
                    .timestamp(Instant.now())
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseEntity
                    .unprocessableEntity()
                    .body(customResponseDto);
        }


    }

    @GetMapping("/getprofile")
    public ResponseEntity<Resource> getProfile(HttpServletRequest request) throws Exception {

        Resource resource = userService.getProfile();

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ignored) {
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
