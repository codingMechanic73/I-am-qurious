package com.example.qurious.repository;

import com.example.qurious.entity.PostEntity;
import com.example.qurious.entity.TopicEntity;
import com.example.qurious.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByTopic(TopicEntity topic);

    List<PostEntity> findByUser(UserEntity user);
}
