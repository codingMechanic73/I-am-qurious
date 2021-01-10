package com.example.qurious.service;

import com.example.qurious.dto.TopicRequestDto;
import com.example.qurious.dto.TopicResponseDto;
import com.example.qurious.entity.TopicEntity;
import com.example.qurious.exception.TopicAlreadyExistsException;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.repository.TopicRepository;
import com.example.qurious.util.EntityDtoMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that provides topic related service
 */
@Data
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private final EntityDtoMapper entityDtoMapper;

    /**
     * Save the topic
     *
     * @param topicRequestDto user input
     * @return saved entity
     * @throws TopicAlreadyExistsException exception stating topic user wants to create already exist
     */
    @Transactional
    public TopicResponseDto save(TopicRequestDto topicRequestDto) throws TopicAlreadyExistsException {
        if (checkIfTopicExists(topicRequestDto.getTopicName())) {
            throw new TopicAlreadyExistsException(topicRequestDto.getTopicName());
        } else {
            TopicEntity topicEntity = entityDtoMapper.topicRequestDtoToTopicEntity(topicRequestDto);
            topicRepository.save(topicEntity);
            return entityDtoMapper.topicEntityToTopicResponseDto(topicEntity);
        }
    }

    /**
     * Get all the topics
     *
     * @return All the topics that are present
     */
    @Transactional(readOnly = true)
    public List<TopicResponseDto> getAllTopics() {
        return topicRepository
                .findAll()
                .stream()
                .map(entityDtoMapper::topicEntityToTopicResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all posts with given topicId
     *
     * @param topicId user input
     * @return Topic related to the given topicId
     * @throws TopicNotFoundException topic related to topicId not found
     */
    @Transactional(readOnly = true)
    public TopicResponseDto getTopic(Long topicId) throws TopicNotFoundException {
        TopicEntity topicEntity = topicRepository
                .findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));
        return entityDtoMapper.topicEntityToTopicResponseDto(topicEntity);
    }

    /**
     * Returns if the the topic exists
     *
     * @param topicName user input
     * @return true if the topic exists
     */
    public boolean checkIfTopicExists(String topicName) {
        return topicRepository.findByTopicName(topicName).isPresent();
    }

}
