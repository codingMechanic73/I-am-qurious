package com.example.qurious.dto;

import com.example.qurious.enums.VoteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDto {

    private Long postId;
    private VoteTypeEnum voteType;

}
