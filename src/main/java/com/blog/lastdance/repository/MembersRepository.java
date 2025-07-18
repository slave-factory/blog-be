package com.blog.lastdance.repository;

import com.blog.lastdance.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Integer> {
    Members findById(Long id);
    Optional<Members> findByUserId(String userId);
    Optional<Members> findByNickname(String nickName);
}
