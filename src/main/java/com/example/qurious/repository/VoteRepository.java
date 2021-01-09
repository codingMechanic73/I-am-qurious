package com.example.qurious.repository;

import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.UserEntity;
import com.example.qurious.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    Optional<VoteEntity> findTopByPostAndUser(PostEntity post, UserEntity user);

}
