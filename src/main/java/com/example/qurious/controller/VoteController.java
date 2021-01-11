package com.example.qurious.controller;

import com.example.qurious.dto.CustomResponseDto;
import com.example.qurious.dto.VoteRequestDto;
import com.example.qurious.exception.AlreadyVotedException;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(description = "Endpoints to cast likes and dislikes")
public class VoteController {

    private final VoteService voteService;

    @ApiOperation(value = "Add vote based on Post Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post successfully added"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
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
