package com.example.qurious.service;

import com.example.qurious.dto.PostRequestDto;
import com.example.qurious.dto.PostResponseDto;
import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.TopicEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.exception.UserNotFoundException;
import com.example.qurious.repository.PostRepository;
import com.example.qurious.repository.TopicRepository;
import com.example.qurious.repository.UserRepository;
import com.example.qurious.util.EntityDtoMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that provides post related service
 */
@Data
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    private final EntityDtoMapper entityDtoMapper;

    /**
     * Save the post
     *
     * @param postRequestDto user input
     * @return saved entity
     * @throws TopicNotFoundException topic related to post not found
     */
    @Transactional
    public PostResponseDto save(PostRequestDto postRequestDto) throws TopicNotFoundException {
        PostEntity postEntity = entityDtoMapper.postRequestDtoToPostEntity(postRequestDto);
        postRepository.save(postEntity);
        return entityDtoMapper.postEntityToPostResponseDto(postEntity);
    }

    /**
     * Get all posts with given topicId
     *
     * @param topicId user input
     * @return All posts with the given topicId
     * @throws TopicNotFoundException topic related to topicId not found
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostByTopic(Long topicId) throws TopicNotFoundException {
        TopicEntity topicEntity = topicRepository
                .findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));
        return postRepository
                .findByTopic(topicEntity)
                .stream()
                .map(entityDtoMapper::postEntityToPostResponseDto)
                .collect(Collectors.toList());

    }

    /**
     * Get all posts with given userName
     *
     * @param userName user input
     * @return All posts with the given userName
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostByUserName(String userName) throws UserNotFoundException {
        UserEntity user = userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
        return postRepository
                .findByUser(user)
                .stream()
                .map(entityDtoMapper::postEntityToPostResponseDto)
                .collect(Collectors.toList());
    }

}
