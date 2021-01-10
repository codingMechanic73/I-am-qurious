package com.example.qurious.controller;

import com.example.qurious.dto.TopicRequestDto;
import com.example.qurious.dto.TopicResponseDto;
import com.example.qurious.exception.TopicAlreadyExistsException;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.service.TopicService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicResponseDto> addTopic(@RequestBody TopicRequestDto topicRequestDto)
            throws TopicAlreadyExistsException {
        TopicResponseDto topicResponseDto = topicService.save(topicRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(topicResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDto>> getAllTopics() {
        List<TopicResponseDto> topics = topicService.getAllTopics();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topics);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDto> getTopic(@PathVariable("topicId") Long topicId)
            throws TopicNotFoundException {
        TopicResponseDto topicResponseDto = topicService.getTopic(topicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicResponseDto);
    }

}
