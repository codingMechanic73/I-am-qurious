package com.example.qurious.controller;

import com.example.qurious.dto.PostRequestDto;
import com.example.qurious.dto.PostResponseDto;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.service.PostService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto postRequestDto)
            throws TopicNotFoundException {
        PostResponseDto postResponseDto = postService.save(postRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postResponseDto);
    }


    @GetMapping("/search-by-topic-id/{topicId}")
    public ResponseEntity<List<PostResponseDto>> getPostByTopicId(@PathVariable("topicId") Long topicId)
            throws TopicNotFoundException {
        List<PostResponseDto> postResponseDto = postService.getPostByTopic(topicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @GetMapping("/search-by-username/{user-name}")
    public ResponseEntity<List<PostResponseDto>> getPostByUserName(@PathVariable("user-name") String userName)
            throws UserNotFoundException {
        List<PostResponseDto> postResponseDto = postService.getPostByUserName(userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResponseDto);
    }

}
