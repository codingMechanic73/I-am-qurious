package com.example.qurious.controller;

import com.example.qurious.dto.CommentRequestDto;
import com.example.qurious.dto.CommentResponseDto;
import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.service.CommentService;
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
@RequestMapping("/api/v1/comment")
@Api(description = "Endpoints to add and view comments based on Post Id or User Name")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "Add Comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment added successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentRequestDto)
            throws PostNotFoundException {
        CommentResponseDto commentResponseDto = commentService.save(commentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponseDto);
    }

    @ApiOperation(value = "Get all comments based on Post Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comments successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/search-by-id/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForPostId(@PathVariable("postId") Long postId)
            throws PostNotFoundException {
        List<CommentResponseDto> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @ApiOperation(value = "Get all comments based on User Name", response = CustomResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comments successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/search-by-username/{userName}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForUserName(@PathVariable("userName") String userName) throws UserNotFoundException {
        List<CommentResponseDto> comments = commentService.getCommentsForUser(userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

}
