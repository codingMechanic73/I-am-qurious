package com.example.qurious.entity;

import com.example.qurious.enums.VoteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VOTE")
public class VoteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity votedBy;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private PostEntity post;

    @Column(name = "vote_type")
    private VoteTypeEnum voteType;

    private Instant votedOn;

}
