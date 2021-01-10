package com.example.qurious.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class PostEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    @Version
    private int version;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 50, message = "Max of 50 characters allowed for Post Title")
    private String title;

    @NotBlank(message = "Please give some description about the post")
    @Size(max = 250, message = "Max of 250 characters allowed for Post Description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    private Integer likeCount;
    private Integer dislikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserEntity user;

    private Instant postedOn;

}
