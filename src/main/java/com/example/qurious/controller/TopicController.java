package com.example.qurious.controller;

import com.example.qurious.dto.TopicRequestDto;
import com.example.qurious.dto.TopicResponseDto;
import com.example.qurious.exception.TopicAlreadyExistsException;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.service.TopicService;
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
@RequestMapping("/api/v1/topic")
@Api(description = "Endpoints to add and view topics based on Topic Id")
public class TopicController {

    private final TopicService topicService;

    @ApiOperation(value = "Add topic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post successfully added"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    public ResponseEntity<TopicResponseDto> addTopic(@RequestBody TopicRequestDto topicRequestDto)
            throws TopicAlreadyExistsException {
        TopicResponseDto topicResponseDto = topicService.save(topicRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(topicResponseDto);
    }

    @ApiOperation(value = "Get all topics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post successfully added"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping
    public ResponseEntity<List<TopicResponseDto>> getAllTopics() {
        List<TopicResponseDto> topics = topicService.getAllTopics();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topics);
    }

    @ApiOperation(value = "Get topic based on Topic Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post successfully added"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDto> getTopic(@PathVariable("topicId") Long topicId)
            throws TopicNotFoundException {
        TopicResponseDto topicResponseDto = topicService.getTopic(topicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicResponseDto);
    }

}
