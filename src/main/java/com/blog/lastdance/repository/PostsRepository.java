package com.blog.lastdance.repository;

import com.blog.lastdance.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findById(Long id);
    Optional<Posts> findByAuthorId(Long authorId);
    Optional<Posts> findByCreatedAt(LocalDateTime createdAt);
}
