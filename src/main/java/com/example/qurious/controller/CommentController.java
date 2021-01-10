package com.example.qurious.controller;

import com.example.qurious.dto.CommentRequestDto;
import com.example.qurious.dto.CommentResponseDto;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.service.CommentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentRequestDto)
            throws PostNotFoundException {
        CommentResponseDto commentResponseDto = commentService.save(commentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponseDto);
    }

    @GetMapping("/search-by-id/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForPostId(@PathVariable("postId") Long postId)
            throws PostNotFoundException {
        List<CommentResponseDto> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @GetMapping("/search-by-username/{userName}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForUserName(@PathVariable("userName") String userName) throws UserNotFoundException {
        List<CommentResponseDto> comments = commentService.getCommentsForUser(userName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

}
