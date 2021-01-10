package com.example.qurious.service;

import com.example.qurious.dto.VoteRequestDto;
import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.entity.VoteEntity;
import com.example.qurious.enums.VoteTypeEnum;
import com.example.qurious.exception.AlreadyVotedException;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.repository.PostRepository;
import com.example.qurious.repository.VoteRepository;
import com.example.qurious.util.EntityDtoMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class that provides vote related service
 */
@Data
@Service
public class VoteService {

    private final AuthService authService;

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;

    private final EntityDtoMapper entityDtoMapper;

    /**
     * Save the vote
     *
     * @param voteRequestDto user input
     * @return saved entity
     * @throws PostNotFoundException exception stating post related to user input not found
     * @throws AlreadyVotedException exception stating user has already voted
     */
    public VoteEntity save(VoteRequestDto voteRequestDto) throws PostNotFoundException, AlreadyVotedException {
        UserEntity user = authService.getCurrentUser();

        PostEntity postEntity = postRepository.findById(voteRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(voteRequestDto.getPostId().toString()));

        Optional<VoteEntity> previousVote = voteRepository.findTopByPostAndUser(postEntity, user);
        VoteEntity voteEntity;

        if (previousVote.isPresent()) {
            voteEntity = previousVote.get();
            if (voteEntity.getVoteType().equals(voteRequestDto.getVoteType())) {
                throw new AlreadyVotedException(voteRequestDto.getVoteType().name());
            }

            if (voteEntity.getVoteType().equals(VoteTypeEnum.LIKED)) {
                postEntity.setLikeCount(postEntity.getLikeCount() - 1);
                postEntity.setDislikeCount(postEntity.getDislikeCount() + 1);
            } else if (voteEntity.getVoteType().equals(VoteTypeEnum.DISLIKED)) {
                postEntity.setDislikeCount(postEntity.getDislikeCount() - 1);
                postEntity.setLikeCount(postEntity.getLikeCount() + 1);
            }
            voteEntity.setVoteType(voteRequestDto.getVoteType());

        } else {
            voteEntity = entityDtoMapper.voteRequestDtoToVoteEntity(voteRequestDto);
            if (voteRequestDto.getVoteType().equals(VoteTypeEnum.LIKED)) {
                postEntity.setLikeCount(postEntity.getLikeCount() + 1);
            } else if (voteRequestDto.getVoteType().equals(VoteTypeEnum.DISLIKED)) {
                postEntity.setDislikeCount(postEntity.getDislikeCount() + 1);
            }
        }
        voteRepository.save(voteEntity);
        return voteEntity;
    }

}
