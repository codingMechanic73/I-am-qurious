package com.example.qurious.repository;

import com.example.qurious.entity.CommentEntity;
import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByPost(PostEntity post);

    List<CommentEntity> findAllByUser(UserEntity user);

}
