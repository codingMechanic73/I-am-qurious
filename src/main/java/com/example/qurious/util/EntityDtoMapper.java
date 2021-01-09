package com.example.qurious.util;

import com.example.qurious.dto.*;
import com.example.qurious.entity.*;
import com.example.qurious.enums.VoteTypeEnum;
import com.example.qurious.exception.CriticalException;
import com.example.qurious.exception.PostNotFoundException;
import com.example.qurious.exception.TopicNotFoundException;
import com.example.qurious.repository.*;
import com.example.qurious.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Utility class used to map dto and entity
 */
@Data
@Component
public class EntityDtoMapper {

    private final AuthService authService;

    private final RoleRepository roleRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public UserEntity signUpRequestDtoToUserEntity(SignUpRequestDto signUpRequestDto) throws CriticalException {
        UserEntity user = new UserEntity();
        user.setUserName(signUpRequestDto.getUserName());

        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setFirstName(signUpRequestDto.getFirstName());
        userDetailsEntity.setLastName(signUpRequestDto.getLastName());
        userDetailsEntity.setEmail(signUpRequestDto.getEmail());
        userDetailsEntity.setProfileUrl(signUpRequestDto.getProfileUrl());
        userDetailsEntity.setCreatedOn(Instant.now());

        RoleEntity role = roleRepository
                .findById(2L)
                .orElseThrow(() -> new CriticalException("No role found with id 2!"));

        user.setRole(role);
        user.setUserDetails(userDetailsEntity);
        user.setVerified(true);
        return user;
    }

    public TopicEntity topicRequestDtoToTopicEntity(TopicRequestDto topicRequestDto) {
        UserEntity user = authService.getCurrentUser();
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicName(topicRequestDto.getTopicName());
        topicEntity.setDescription(topicRequestDto.getTopicDescription());
        topicEntity.setUser(user);
        topicEntity.setCreatedOn(Instant.now());
        topicEntity.setPosts(new ArrayList<>());
        topicEntity.setTopicPicture("defaultTopicPicture.jpg");
        return topicEntity;
    }


    public TopicResponseDto topicEntityToTopicResponseDto(TopicEntity topicEntity) {
        return TopicResponseDto
                .builder()
                .topicId(topicEntity.getTopicId())
                .topicName(topicEntity.getTopicName())
                .topicDescription(topicEntity.getDescription())
                .noOfPosts(topicEntity.getPosts().size())
                .createdBy(topicEntity.getUser().getUserName())
                .topicPicture(topicEntity.getTopicPicture())
                .createdTimeAgo(TimeAgo.using(topicEntity.getCreatedOn().toEpochMilli()))
                .build();
    }

    public PostEntity postRequestDtoToPostEntity(PostRequestDto postRequestDto)
            throws TopicNotFoundException {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postRequestDto.getPostTitle());
        postEntity.setDescription(postRequestDto.getPostDescription());
        postEntity.setVoteCount(0);
        postEntity.setPostedOn(Instant.now());
        TopicEntity topicEntity = topicRepository
                .findById(postRequestDto
                        .getTopicId()).orElseThrow(() -> new TopicNotFoundException(postRequestDto.getTopicId().toString()));
        postEntity.setTopic(topicEntity);
        postEntity.setUser(authService.getCurrentUser());
        return postEntity;
    }

    public PostResponseDto postEntityToPostResponseDto(PostEntity postEntity) {
        return PostResponseDto
                .builder()
                .postId(postEntity.getPostId())
                .postTitle(postEntity.getTitle())
                .postDescription(postEntity.getDescription())
                .topicId(postEntity.getTopic().getTopicId())
                .topicName(postEntity.getTopic().getTopicName())
                .postedBy(postEntity.getUser().getUserName())
                .postedTimeAgo(TimeAgo.using(postEntity.getPostedOn().toEpochMilli()))
                .commentCount(commentRepository.findByPost(postEntity).size())
                .isUpVoted(checkVoteType(postEntity, authService.getCurrentUser(), VoteTypeEnum.UP_VOTE))
                .isDownVoted(checkVoteType(postEntity, authService.getCurrentUser(), VoteTypeEnum.DOWN_VOTE))
                .voteCount(postEntity.getVoteCount())
                .build();
    }

    private boolean checkVoteType(PostEntity postEntity, UserEntity userEntity, VoteTypeEnum voteTypeEnum) {
        VoteEntity voteEntity = voteRepository
                .findTopByPostAndUser(postEntity, userEntity)
                .orElse(new VoteEntity());
        return voteTypeEnum.equals(voteEntity.getVoteType());
    }

    public CommentEntity commentRequestDtoToCommentEntity(CommentRequestDto commentRequestDto)
            throws PostNotFoundException {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(commentRequestDto.getComment());

        commentEntity
                .setPost(postRepository.findById(commentRequestDto.getPostId())
                        .orElseThrow(() -> new PostNotFoundException(commentRequestDto.getPostId().toString())));
        commentEntity.setCommentedOn(Instant.now());
        commentEntity.setUser(authService.getCurrentUser());
        return commentEntity;
    }

    public CommentResponseDto commentEntityToPostResponseDto(CommentEntity commentEntity) {
        return CommentResponseDto
                .builder()
                .commentId(commentEntity.getCommentId())
                .comment(commentEntity.getComment())
                .postId(commentEntity.getPost().getPostId())
                .commentedBy(commentEntity.getUser().getUserName())
                .postName(commentEntity.getPost().getTitle())
                .commentedTimeAgo(TimeAgo.using(commentEntity.getCommentedOn().toEpochMilli()))
                .commentedBy(commentEntity.getUser().getUserName())
                .build();
    }

    public VoteEntity voteRequestDtoToVoteEntity(VoteRequestDto voteRequestDto) throws PostNotFoundException {
        PostEntity postEntity = postRepository
                .findById(voteRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(voteRequestDto.getPostId().toString()));

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setPost(postEntity);
        voteEntity.setUser(authService.getCurrentUser());
        voteEntity.setVotedOn(Instant.now());
        voteEntity.setVoteType(voteRequestDto.getVoteType());
        return voteEntity;
    }

}
