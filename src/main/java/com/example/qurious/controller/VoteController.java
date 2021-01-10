package com.example.qurious.controller;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.dto.VoteRequestDto;
import com.example.qurious.exception.AlreadyVotedException;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.service.VoteService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Data
@RestController
@RequestMapping("/api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<CustomResponseDto> addVote(@RequestBody VoteRequestDto voteRequestDto)
            throws AlreadyVotedException,
            PostNotFoundException {

        voteService.save(voteRequestDto);
        CustomResponseDto customResponse = CustomResponseDto
                .builder()
                .message("Successfully " + voteRequestDto.getVoteType())
                .timestamp(Instant.now())
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customResponse);
    }

}
