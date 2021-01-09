package com.example.qurious.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TOPIC")
public class TopicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long topicId;

    @Version
    private int version;

    @NotBlank(message = "Topic cannot be blank")
    @Size(min = 5, max = 50, message = "Topic must have a length between 5 and 50 characters")
    private String topicName;

    @Size(max = 250, message = "Max of 250 characters allowed for description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topic")
    private List<PostEntity> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity user;

    private String topicPicture;

    private Instant createdOn;

}
