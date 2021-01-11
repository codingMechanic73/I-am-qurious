package com.example.qurious.controller;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.dto.PostRequestDto;
import com.example.qurious.dto.PostResponseDto;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/post")
@Api(description = "Endpoints to add and view posts based on Post Id or User Name")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "Add post based on Topic Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post successfully added"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto postRequestDto)
            throws TopicNotFoundException {
        PostResponseDto postResponseDto = postService.save(postRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postResponseDto);
    }


    @ApiOperation(value = "Get all posts based on Topic Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Posts successfully retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/search-by-topic-id/{topicId}")
    public ResponseEntity<List<PostResponseDto>> getPostByTopicId(@PathVariable("topicId") Long topicId)
            throws TopicNotFoundException {
        List<PostResponseDto> postResponseDto = postService.getPostByTopic(topicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResponseDto);
    }

    @ApiOperation(value = "Get all posts based on User Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Posts successfully retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/search-by-username/{user-name}")
    public ResponseEntity<List<PostResponseDto>> getPostByUserName(@PathVariable("user-name") String userName)
            throws UserNotFoundException {
        List<PostResponseDto> postResponseDto = postService.getPostByUserName(userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResponseDto);
    }

}
