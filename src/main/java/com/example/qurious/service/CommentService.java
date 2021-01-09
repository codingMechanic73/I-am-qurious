package com.example.qurious.service;

import com.example.qurious.dto.CommentRequestDto;
import com.example.qurious.dto.CommentResponseDto;
import com.example.qurious.entity.CommentEntity;
import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.repository.CommentRepository;
import com.example.qurious.repository.PostRepository;
import com.example.qurious.repository.UserRepository;
import com.example.qurious.util.EntityDtoMapper;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Class that provides comment related service
 */
@Data
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final EntityDtoMapper entityDtoMapper;

    /**
     * Save the comment
     *
     * @param commentRequestDto user input
     * @return saved entity
     * @throws PostNotFoundException post related to comment not found
     */
    @Transactional
    public CommentEntity save(CommentRequestDto commentRequestDto) throws
            PostNotFoundException {
        CommentEntity commentEntity = entityDtoMapper.commentRequestDtoToCommentEntity(commentRequestDto);
        commentRepository.save(commentEntity);
        return commentEntity;
    }

    /**
     * Get all comments with given postId
     *
     * @param postId user input
     * @return All comments with the given postId
     * @throws PostNotFoundException post related to postId not found
     */
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsForPost(Long postId) throws PostNotFoundException {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(postEntity)
                .stream()
                .map(entityDtoMapper::commentEntityToPostResponseDto).collect(toList());
    }

    /**
     * Get all comments with given postId
     *
     * @param userName user input
     * @return All comments with the given userName
     * @throws UsernameNotFoundException user related to userName not found
     */
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsForUser(String userName) {
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(entityDtoMapper::commentEntityToPostResponseDto)
                .collect(toList());
    }
}
